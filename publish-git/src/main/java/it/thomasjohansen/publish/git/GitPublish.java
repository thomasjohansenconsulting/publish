package it.thomasjohansen.publish.git;

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
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.eclipse.jgit.lib.Constants.HEAD;

@Service
public class GitPublish {

    private String repositoryUri = "https://github.com/thxmasj/site.git";
    private String directory = "/tmp/site";
    private String branch = "master";

    public List<Title> index() {
        return listHead(repository(repositoryUri, directory));
    }

    private Repository repository(String repositoryUri, String targetDirectory) {
        if (!new File(targetDirectory).exists())
            return cloneRepository(repositoryUri, targetDirectory);
        return loadRepository(targetDirectory);
    }

    private Repository cloneRepository(String repositoryUri, String targetDirectory) {
        try {
            return Git.cloneRepository()
                    .setURI(repositoryUri)
                    .setDirectory(new File(targetDirectory))
                    .call()
                    .getRepository();
        } catch (GitAPIException e) {
            throw new RuntimeException("Failed to clone repo", e);
        }
    }

    private Repository loadRepository(String targetDirectory) {
        try {
            return FileRepositoryBuilder.create(new File(targetDirectory, ".git"));
        } catch (IOException e) {
            throw new RuntimeException("Failed to load repository", e);
        }
    }

    private List<Title> listHead(Repository repository) {
        try {
            Ref head = repository.findRef(HEAD);
            RevCommit commit = new RevWalk(repository).parseCommit(head.getObjectId());
            RevTree tree = commit.getTree();
            TreeWalk treeWalk = new TreeWalk(repository);
            treeWalk.addTree(tree);
            treeWalk.setRecursive(true);
            treeWalk.setFilter(PathSuffixFilter.create(".md"));
            List<Title> titles = new ArrayList<Title>();
            while (treeWalk.next())
                titles.add(Title.builder().name(titleName(treeWalk.getPathString())).build());
            return titles;
        } catch (IOException e) {
            throw new RuntimeException("Failed to list HEAD", e);
        }
    }

    private String titleName(String pathString) {
        return pathString.replace('_', ' ').substring(0, pathString.length() - 3);
    }

}
