/**
 * 계정 관리 JavaScript
 * 계정 목록, 생성, 수정, 삭제 기능을 제공
 */

class AccountManager {
    constructor() {
        this.apiBaseUrl = '/api/account';
        this.currentPage = 1;
        this.pageSize = 10;
        this.init();
    }

    init() {
        this.bindEvents();
        this.loadAccounts();
        this.loadAccountStats();
    }

    bindEvents() {
        // 검색 버튼
        document.getElementById('searchBtn')?.addEventListener('click', () => this.searchAccounts());
        
        // 새로고침 버튼
        document.getElementById('refreshBtn')?.addEventListener('click', () => this.loadAccounts());
        
        // 계정 생성 버튼
        document.getElementById('createAccountBtn')?.addEventListener('click', () => this.showCreateModal());
        
        // 모달 닫기 버튼들
        document.querySelectorAll('.modal-close').forEach(btn => {
            btn.addEventListener('click', () => this.closeModals());
        });
        
        // 계정 생성 폼 제출
        document.getElementById('createAccountForm')?.addEventListener('submit', (e) => {
            e.preventDefault();
            this.createAccount();
        });
        
        // 계정 수정 폼 제출
        document.getElementById('updateAccountForm')?.addEventListener('submit', (e) => {
            e.preventDefault();
            this.updateAccount();
        });
    }

    async loadAccounts(page = 1) {
        try {
            this.showLoading();
            
            const params = new URLSearchParams({
                page: page,
                size: this.pageSize
            });
            
            // 검색 조건 추가
            const searchKeyword = document.getElementById('searchKeyword')?.value;
            const accountType = document.getElementById('accountType')?.value;
            const status = document.getElementById('status')?.value;
            
            if (searchKeyword) params.append('searchKeyword', searchKeyword);
            if (accountType) params.append('accountType', accountType);
            if (status) params.append('status', status);
            
            const response = await fetch(`${this.apiBaseUrl}/list?${params}`);
            const result = await response.json();
            
            if (result.success) {
                this.renderAccountList(result.data);
                this.renderPagination(result.pagination);
            } else {
                this.showError('계정 목록을 불러오는데 실패했습니다: ' + result.error);
            }
        } catch (error) {
            this.showError('계정 목록을 불러오는데 실패했습니다: ' + error.message);
        } finally {
            this.hideLoading();
        }
    }

    async searchAccounts() {
        this.currentPage = 1;
        await this.loadAccounts(1);
    }

    async loadAccountStats() {
        try {
            const response = await fetch(`${this.apiBaseUrl}/stats`);
            const result = await response.json();
            
            if (result.success) {
                this.renderAccountStats(result.data);
            }
        } catch (error) {
            console.error('통계 로딩 실패:', error);
        }
    }

    renderAccountList(accounts) {
        const tbody = document.getElementById('accountTableBody');
        if (!tbody) return;
        
        tbody.innerHTML = '';
        
        if (accounts.length === 0) {
            tbody.innerHTML = '<tr><td colspan="8" class="text-center">데이터가 없습니다.</td></tr>';
            return;
        }
        
        accounts.forEach(account => {
            const row = document.createElement('tr');
            row.innerHTML = `
                <td>${account.accountNumber || '-'}</td>
                <td>${account.name || '-'}</td>
                <td>${account.accountType || '-'}</td>
                <td>${account.status || '-'}</td>
                <td>${account.currency || '-'}</td>
                <td class="text-end">${this.formatCurrency(account.netAmount)}</td>
                <td>${this.formatDate(account.createdDate)}</td>
                <td>
                    <button class="btn btn-sm btn-outline-primary" onclick="accountManager.showDetailModal('${account.accountNumber}')">
                        상세
                    </button>
                    <button class="btn btn-sm btn-outline-warning" onclick="accountManager.showUpdateModal('${account.accountNumber}')">
                        수정
                    </button>
                    <button class="btn btn-sm btn-outline-danger" onclick="accountManager.deleteAccount('${account.accountNumber}')">
                        삭제
                    </button>
                </td>
            `;
            tbody.appendChild(row);
        });
    }

    renderPagination(pagination) {
        const paginationContainer = document.getElementById('pagination');
        if (!paginationContainer) return;
        
        const { page, totalPages, total } = pagination;
        
        let paginationHTML = `
            <div class="d-flex justify-content-between align-items-center">
                <div>총 ${total}개 중 ${((page - 1) * this.pageSize) + 1}-${Math.min(page * this.pageSize, total)}개</div>
                <nav>
                    <ul class="pagination pagination-sm mb-0">
        `;
        
        // 이전 페이지
        if (page > 1) {
            paginationHTML += `<li class="page-item"><a class="page-link" href="#" onclick="accountManager.loadAccounts(${page - 1})">이전</a></li>`;
        }
        
        // 페이지 번호들
        for (let i = Math.max(1, page - 2); i <= Math.min(totalPages, page + 2); i++) {
            paginationHTML += `
                <li class="page-item ${i === page ? 'active' : ''}">
                    <a class="page-link" href="#" onclick="accountManager.loadAccounts(${i})">${i}</a>
                </li>
            `;
        }
        
        // 다음 페이지
        if (page < totalPages) {
            paginationHTML += `<li class="page-item"><a class="page-link" href="#" onclick="accountManager.loadAccounts(${page + 1})">다음</a></li>`;
        }
        
        paginationHTML += `
                    </ul>
                </nav>
            </div>
        `;
        
        paginationContainer.innerHTML = paginationHTML;
    }

    renderAccountStats(stats) {
        const statsContainer = document.getElementById('accountStats');
        if (!statsContainer) return;
        
        statsContainer.innerHTML = `
            <div class="row">
                <div class="col-md-3">
                    <div class="card bg-primary text-white">
                        <div class="card-body">
                            <h5 class="card-title">총 계정</h5>
                            <h3>${stats.totalAccounts}</h3>
                        </div>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="card bg-success text-white">
                        <div class="card-body">
                            <h5 class="card-title">활성 계정</h5>
                            <h3>${stats.activeAccounts}</h3>
                        </div>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="card bg-warning text-white">
                        <div class="card-body">
                            <h5 class="card-title">총 잔액</h5>
                            <h3>${this.formatCurrency(stats.totalBalance)}</h3>
                        </div>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="card bg-info text-white">
                        <div class="card-body">
                            <h5 class="card-title">평균 잔액</h5>
                            <h3>${this.formatCurrency(stats.averageBalance)}</h3>
                        </div>
                    </div>
                </div>
            </div>
        `;
    }

    showCreateModal() {
        document.getElementById('createAccountModal').style.display = 'block';
        document.getElementById('createAccountForm').reset();
    }

    async showDetailModal(accountNumber) {
        try {
            const response = await fetch(`${this.apiBaseUrl}/read?accountId=${accountNumber}`);
            const result = await response.json();
            
            if (result.success) {
                const account = result.data;
                document.getElementById('detailAccountNumber').textContent = account.accountNumber;
                document.getElementById('detailName').textContent = account.name;
                document.getElementById('detailAccountType').textContent = account.accountType;
                document.getElementById('detailStatus').textContent = account.status;
                document.getElementById('detailCurrency').textContent = account.currency;
                document.getElementById('detailBalance').textContent = this.formatCurrency(account.netAmount);
                document.getElementById('detailInterestRate').textContent = account.interestRate + '%';
                document.getElementById('detailCreatedDate').textContent = this.formatDate(account.createdDate);
                
                document.getElementById('accountDetailModal').style.display = 'block';
            } else {
                this.showError('계정 정보를 불러오는데 실패했습니다: ' + result.error);
            }
        } catch (error) {
            this.showError('계정 정보를 불러오는데 실패했습니다: ' + error.message);
        }
    }

    async showUpdateModal(accountNumber) {
        try {
            const response = await fetch(`${this.apiBaseUrl}/read?accountId=${accountNumber}`);
            const result = await response.json();
            
            if (result.success) {
                const account = result.data;
                
                // 폼에 데이터 설정
                document.getElementById('updateAccountNumber').value = account.accountNumber;
                document.getElementById('updateName').value = account.name;
                document.getElementById('updateAccountType').value = account.accountType;
                document.getElementById('updateStatus').value = account.status;
                document.getElementById('updateCurrency').value = account.currency;
                document.getElementById('updateBalance').value = account.netAmount;
                document.getElementById('updateInterestRate').value = account.interestRate;
                
                document.getElementById('updateAccountModal').style.display = 'block';
            } else {
                this.showError('계정 정보를 불러오는데 실패했습니다: ' + result.error);
            }
        } catch (error) {
            this.showError('계정 정보를 불러오는데 실패했습니다: ' + error.message);
        }
    }

    async createAccount() {
        try {
            const formData = new FormData(document.getElementById('createAccountForm'));
            const accountData = {
                accountNumber: formData.get('accountNumber'),
                name: formData.get('name'),
                accountType: formData.get('accountType'),
                status: formData.get('status'),
                currency: formData.get('currency'),
                netAmount: formData.get('netAmount'),
                interestRate: formData.get('interestRate'),
                password: formData.get('password'),
                identificationNumber: formData.get('identificationNumber')
            };
            
            const response = await fetch(`${this.apiBaseUrl}/create`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(accountData)
            });
            
            const result = await response.json();
            
            if (result.success) {
                this.showSuccess('계정이 성공적으로 생성되었습니다.');
                this.closeModals();
                this.loadAccounts();
                this.loadAccountStats();
            } else {
                this.showError('계정 생성에 실패했습니다: ' + result.error);
            }
        } catch (error) {
            this.showError('계정 생성에 실패했습니다: ' + error.message);
        }
    }

    async updateAccount() {
        try {
            const formData = new FormData(document.getElementById('updateAccountForm'));
            const accountData = {
                name: formData.get('name'),
                accountType: formData.get('accountType'),
                status: formData.get('status'),
                currency: formData.get('currency'),
                netAmount: formData.get('netAmount'),
                interestRate: formData.get('interestRate'),
                password: formData.get('password'),
                identificationNumber: formData.get('identificationNumber')
            };
            
            const accountNumber = formData.get('accountNumber');
            const response = await fetch(`${this.apiBaseUrl}/update`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({...accountData, accountNumber: accountNumber})
            });
            
            const result = await response.json();
            
            if (result.success) {
                this.showSuccess('계정이 성공적으로 수정되었습니다.');
                this.closeModals();
                this.loadAccounts();
                this.loadAccountStats();
            } else {
                this.showError('계정 수정에 실패했습니다: ' + result.error);
            }
        } catch (error) {
            this.showError('계정 수정에 실패했습니다: ' + error.message);
        }
    }

    async deleteAccount(accountNumber) {
        if (!confirm('정말로 이 계정을 삭제하시겠습니까?')) {
            return;
        }
        
        try {
            const response = await fetch(`${this.apiBaseUrl}/delete?accountId=${accountNumber}`, {
                method: 'GET'
            });
            
            const result = await response.json();
            
            if (result.success) {
                this.showSuccess('계정이 성공적으로 삭제되었습니다.');
                this.loadAccounts();
                this.loadAccountStats();
            } else {
                this.showError('계정 삭제에 실패했습니다: ' + result.error);
            }
        } catch (error) {
            this.showError('계정 삭제에 실패했습니다: ' + error.message);
        }
    }

    closeModals() {
        document.querySelectorAll('.modal').forEach(modal => {
            modal.style.display = 'none';
        });
    }

    showLoading() {
        const loading = document.getElementById('loading');
        if (loading) loading.style.display = 'block';
    }

    hideLoading() {
        const loading = document.getElementById('loading');
        if (loading) loading.style.display = 'none';
    }

    showSuccess(message) {
        this.showAlert(message, 'success');
    }

    showError(message) {
        this.showAlert(message, 'danger');
    }

    showAlert(message, type) {
        const alertContainer = document.getElementById('alertContainer');
        if (!alertContainer) return;
        
        const alertHTML = `
            <div class="alert alert-${type} alert-dismissible fade show" role="alert">
                ${message}
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        `;
        
        alertContainer.innerHTML = alertHTML;
        
        // 5초 후 자동 제거
        setTimeout(() => {
            alertContainer.innerHTML = '';
        }, 5000);
    }

    formatCurrency(amount) {
        if (amount == null) return '-';
        return new Intl.NumberFormat('ko-KR', {
            style: 'currency',
            currency: 'KRW'
        }).format(amount);
    }

    formatDate(dateString) {
        if (!dateString) return '-';
        const date = new Date(dateString);
        return date.toLocaleDateString('ko-KR');
    }
}

// 전역 인스턴스 생성
let accountManager;

// DOM 로드 완료 후 초기화
document.addEventListener('DOMContentLoaded', () => {
    accountManager = new AccountManager();
}); 