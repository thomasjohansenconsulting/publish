package it.thomasjohansen.publish;

import it.thomasjohansen.publish.git.GitPublish;
import it.thomasjohansen.publish.model.Title;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.PathSuffixFilter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.eclipse.jgit.lib.Constants.HEAD;

@RestController
public class PublishController {

    private GitPublish publish;

    public PublishController(GitPublish publish) {
        this.publish = publish;
    }

    @GetMapping("/")
    public List<Title> index() {
        return publish.index();
    }

}
