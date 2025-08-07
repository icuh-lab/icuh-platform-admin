package re.kr.icuh.icuhplatformadmin.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
import re.kr.icuh.icuhplatformadmin.domain.Article;
import re.kr.icuh.icuhplatformadmin.domain.ArticleStatus;
import re.kr.icuh.icuhplatformadmin.domain.QArticle;
import re.kr.icuh.icuhplatformadmin.domain.QFileEntity;

import java.util.List;

@Repository
public class ArticleQueryRepository {

    private final JPAQueryFactory queryFactory;

    public ArticleQueryRepository(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    public List<Article> findPendingArticles() {
        QArticle article = QArticle.article;
        BooleanBuilder builder = new BooleanBuilder();

        builder.and(article.status.eq(ArticleStatus.PENDING));

        return queryFactory
                .selectFrom(article)
                .leftJoin(article.documentType).fetchJoin()
                .leftJoin(article.subjectDomain).fetchJoin()
                .where(builder)
                .orderBy(article.createdAt.desc())
                .fetch();
    }

    public List<Article> findApprovedArticles() {
        QArticle article = QArticle.article;
        BooleanBuilder builder = new BooleanBuilder();

        builder.and(article.status.eq(ArticleStatus.APPROVED));

        return queryFactory
                .selectFrom(article)
                .leftJoin(article.documentType).fetchJoin()
                .leftJoin(article.subjectDomain).fetchJoin()
                .where(builder)
                .orderBy(article.createdAt.desc())
                .fetch();
    }

    public Article findArticle(Long id) {
        QArticle article = QArticle.article;
        QFileEntity file = QFileEntity.fileEntity;

        BooleanBuilder builder = new BooleanBuilder();

        return queryFactory
                .selectFrom(article)
                .leftJoin(article.documentType).fetchJoin()
                .leftJoin(article.subjectDomain).fetchJoin()
                .leftJoin(article.files, file).fetchJoin()
                .where(builder.and(article.id.eq(id)))
                .fetchOne();
    }

    public JPAQuery<Long> countApprovedArticles() {
        QArticle article = QArticle.article;

        return queryFactory
                .select(article.count())
                .from(article)
                .where(article.status.eq(ArticleStatus.APPROVED));
    }


}
