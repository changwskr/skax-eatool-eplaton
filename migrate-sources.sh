#!/bin/bash

# SKAX Project Eplaton - Source Migration Script
# 기존 소스 코드를 Maven 표준 디렉토리 구조로 이동

echo "Starting source migration..."

# Create Maven standard directory structure
echo "Creating Maven directory structure..."

# mbc01-java module
mkdir -p mbc01-java/src/main/java
mkdir -p mbc01-java/src/main/resources
mkdir -p mbc01-java/src/test/java
mkdir -p mbc01-java/src/test/resources

# mbc-config module
mkdir -p mbc-config/src/main/java
mkdir -p mbc-config/src/main/resources
mkdir -p mbc-config/src/test/java
mkdir -p mbc-config/src/test/resources

# mbc-java module
mkdir -p mbc-java/src/main/java
mkdir -p mbc-java/src/main/resources
mkdir -p mbc-java/src/test/java
mkdir -p mbc-java/src/test/resources

# Move existing source files
echo "Moving source files..."

# Move mbc01-java source files
if [ -d "mbc01-java/src" ]; then
    echo "Moving mbc01-java source files..."
    cp -r mbc01-java/src/* mbc01-java/src/main/java/ 2>/dev/null || true
fi

# Move mbc-java source files
if [ -d "mbc-java/src" ]; then
    echo "Moving mbc-java source files..."
    cp -r mbc-java/src/* mbc-java/src/main/java/ 2>/dev/null || true
fi

# Move configuration files
echo "Moving configuration files..."

# Move mbc-config files
if [ -d "mbc-config/ifr_dev" ]; then
    echo "Moving mbc-config files..."
    cp -r mbc-config/ifr_dev/* mbc-config/src/main/resources/ 2>/dev/null || true
fi

# Create mapper directory for MyBatis
mkdir -p mbc-java/src/main/resources/mapper

echo "Source migration completed!"
echo ""
echo "Next steps:"
echo "1. Review the moved files in each module"
echo "2. Update package declarations if needed"
echo "3. Run 'mvn clean install' to build the project"
echo "4. Run 'cd mbc-java && mvn spring-boot:run' to start the application" 