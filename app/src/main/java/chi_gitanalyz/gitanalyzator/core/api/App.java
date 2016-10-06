package chi_gitanalyz.gitanalyzator.core.api;

import java.util.concurrent.Executor;

/**
 * Created by Papin on 20.09.2016.
 */
public interface App
{
    Net getNet();
    Db getDb();
    Executor getExecutor();

}
