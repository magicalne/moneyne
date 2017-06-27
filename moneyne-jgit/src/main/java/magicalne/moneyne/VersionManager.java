package magicalne.moneyne;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by magiclane on 26/06/2017.
 */
@Slf4j
public enum VersionManager {
    INSTANCE;

    private static final String REMOTE_REPO = "https://git.coding.net/chandlerlv/policy.git";
    private static final String GIT_USERNAME = "chandler@rupiahplus.com";
    private static final String GIT_PASSWORD = "qwe4416736";

    VersionManager() {

    }

    public void clone(String localPath, String branchName) throws GitAPIException {
        Path path = Paths.get(localPath);
        boolean exists = Files.exists(path);
        if (exists) {
            return;
        }
        Git repo = null;
        try {
            log.info("Clone remote repo to local path.");
            repo = Git
                    .cloneRepository()
                    .setURI(REMOTE_REPO)
                    .setCredentialsProvider(new UsernamePasswordCredentialsProvider(GIT_USERNAME, GIT_PASSWORD))
                    .setBranch(branchName)
                    .setDirectory(new File(localPath))
                    .call();
        } finally {
            if (repo != null) {
                repo.close();
                log.info("Clone repo successfully.");
            }
        }

    }

    public List<String> pull(String localPath, String branchName) throws GitAPIException, IOException {
        Git git = null;
        try {
            Repository repo = new FileRepositoryBuilder().findGitDir(new File(localPath)).build();
            git = new Git(repo);
            //Get the current head.
            final String head = "HEAD^{tree}";
            ObjectId beforePull = repo.resolve(head);
            //Pull head from remote with rebase.
            log.info("Pull with rebase from {} branch.", branchName);
            git
                    .pull()
                    .setCredentialsProvider(new UsernamePasswordCredentialsProvider(GIT_USERNAME, GIT_PASSWORD))
                    .setRemoteBranchName(branchName)
                    .setRebase(true)
                    .call();
            //Get the current head after pulling.
            ObjectId afterPull = repo.resolve(head);

            ObjectReader reader = repo.newObjectReader();
            CanonicalTreeParser oldTreeIterator = new CanonicalTreeParser();
            oldTreeIterator.reset(reader, beforePull);
            CanonicalTreeParser newTreeIterator = new CanonicalTreeParser();
            newTreeIterator.reset(reader, afterPull);
            List<DiffEntry> diffEntryList = git.diff()
                    .setNewTree(newTreeIterator)
                    .setOldTree(oldTreeIterator)
                    .call();
            log.info("Pull with rebase successfully. Diff changes: {}", diffEntryList);

            return diffEntryList.stream()
                    .filter(e -> e.getChangeType() == DiffEntry.ChangeType.ADD ||
                            e.getChangeType() == DiffEntry.ChangeType.MODIFY ||
                            e.getChangeType() == DiffEntry.ChangeType.RENAME)
                    .map(DiffEntry::getNewPath)
                    .collect(Collectors.toList());

        } finally {
            if (git != null) {
                git.close();
            }
        }
    }

//    public void revert(String localPath, String branchName) throws IOException, GitAPIException {
//        FileRepository fileRepository = new FileRepository(localPath);
//        final Git repo = new Git(fileRepository);
//        repo.
//        repo.revert().call();
//    }
}
