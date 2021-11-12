package platform.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import platform.businesslayer.CodeSnippet;

import java.util.List;
import java.util.UUID;

@Repository
public interface CodeSnippetRepository extends CrudRepository<CodeSnippet, Integer> {
    CodeSnippet findCodeSnippetByUuid(UUID uuid);

    List<CodeSnippet> findFirst10ByIdNotNullOrderByDateTimeOfPublicationDesc();
    List<CodeSnippet> findFirst10ByIdNotNullAndTimeLeftIsAndViewsLeftIsOrderByDateTimeOfPublicationDesc(long timeLeft, int viewsLeft);

    void deleteCodeSnippetByUuid(UUID uuid);
}
