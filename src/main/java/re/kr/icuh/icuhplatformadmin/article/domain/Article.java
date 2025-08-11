package re.kr.icuh.icuhplatformadmin.article.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.testcontainers.shaded.com.google.common.hash.Hashing;
import re.kr.icuh.icuhplatformadmin.file.domain.FileEntity;
import re.kr.icuh.icuhplatformadmin.file.domain.FileStatus;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "articles")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "author")
    private String author;

    @Column(name = "author_organization")
    private String authorOrganization;

    @Column(name = "department")
    private String department;

    @Column(name = "temp_password")
    private String tempPassword;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "views")
    private Integer views;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ArticleStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document_type_id")
    private DocumentType documentType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_domain_id")
    private SubjectDomain subjectDomain;

    @Column(name = "source")
    private String source;

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FileEntity> files = new ArrayList<>();

    @Builder
    public Article(String title, String description, String author, String authorOrganization, String department, String tempPassword, Integer views, ArticleStatus status, DocumentType documentType, SubjectDomain subjectDomain, String source) {
        this.title = title;
        this.description = description;
        this.author = author;
        this.authorOrganization = authorOrganization;
        this.department = department;
        this.tempPassword = sha256Encode(tempPassword);
        this.views = views == null ? 0 : views;
        this.status = status == null ? ArticleStatus.PENDING : status;
        this.documentType = documentType;
        this.subjectDomain = subjectDomain;
        this.source = source;
    }

    public String sha256Encode(String tempPassword) {
        return Hashing.sha256().hashString(tempPassword, StandardCharsets.UTF_8).toString();
    }

    // 소프트 삭제 메서드
    public void softDelete() {
        this.status = ArticleStatus.DELETED_PENDING;
        this.updatedAt = LocalDateTime.now();
    }

    // 조회수 증가 메서드
    public void increaseViews() {
        this.views++;
    }

    // 파일 추가 메서드
    public void addFile(FileEntity file) {
        this.files.add(file);
        file.setArticle(this);
    }

    // 비밀번호 검증 메서드
    public boolean validatePassword(String password) {
        // 실제 구현에서는 암호화된 비밀번호 비교 로직 필요
        return this.tempPassword.equals(sha256Encode(password));
    }

    public void changeStatus(ArticleStatus articleStatus) {
        this.status = articleStatus;
    }

    public void updateArticle(ArticleEditRequest articleEditRequest) {
        this.title = articleEditRequest.getTitle();
        this.description = articleEditRequest.getDescription();
        this.author = articleEditRequest.getAuthor();
        this.authorOrganization = articleEditRequest.getAuthorOrganization();
        this.department = articleEditRequest.getDepartment();
        this.source = articleEditRequest.getSource();
        this.updatedAt = LocalDateTime.now();
        this.status = ArticleStatus.APPROVED;

        // 기존 파일 모두 제거
        this.files.clear();

        // FileEditRequest를 FileEntity로 변환하여 추가
        articleEditRequest.getFiles().forEach(fileEditRequest -> {
            FileEntity fileEntity = FileEntity.builder()
                    .article(this)
                    .originalFilename(fileEditRequest.getOriginalFilename())
                    .storedFilename(fileEditRequest.getStoredFilename())
                    .filePath(fileEditRequest.getFilePath())
                    .extension(fileEditRequest.getExtension())
                    .fileSize(fileEditRequest.getFileSize())
                    .build();

            // 파일 상태 설정 (이전 상태를 유지하거나 APPROVED로 설정)
            fileEntity.changeStatus(FileStatus.APPROVED);

            // 파일 추가
            this.addFile(fileEntity);
        });
    }
}
