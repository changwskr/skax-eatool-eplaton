package skcc.arch.biz.common.infrastructure.redis;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Redis Cache Repository 예시
 * 실제 사용 시에는 @RedisHash 어노테이션이 있는 Entity와 함께 사용
 */
@Repository
public interface RedisCacheRepository extends CrudRepository<Object, String> {
    // Redis Repository 메서드들
} 