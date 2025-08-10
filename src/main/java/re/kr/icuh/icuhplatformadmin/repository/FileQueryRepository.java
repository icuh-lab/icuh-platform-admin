package re.kr.icuh.icuhplatformadmin.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
import re.kr.icuh.icuhplatformadmin.domain.QFileEntity;

@Repository
public class FileQueryRepository {

    private final JPAQueryFactory queryFactory;

    public FileQueryRepository(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    public Long findFileByArticleId(Long articleId) {
        QFileEntity file = QFileEntity.fileEntity;

        return queryFactory
                .select(file.id)
                .from(file)
                .where(file.article.id.eq(articleId))
                .fetchOne();
    }
}
