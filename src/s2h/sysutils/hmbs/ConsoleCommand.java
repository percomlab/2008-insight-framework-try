/*
 * ConsoleCommand.java
 *
 * Created on September 17, 2001, 1:18 PM
 */

package s2h.sysutils.hmbs;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * @author nzjuneja
 * @version
 */
public abstract class ConsoleCommand
{

    private static final File DEF_FILE = new File(System.getProperty("user.home") + File.separator + "temp-sim.xml");

    static File s_showFile;

    private String m_name;

    private String m_shortDescription;

    private String m_help;

    protected JConsole m_sim;

    /** Creates new ConsoleCommand */
    public ConsoleCommand(String commandName, String shortDesc, String commandHelp)
    {
        m_name = commandName;
        m_shortDescription = shortDesc;
        m_help = commandHelp;
    }

    protected final File getTempSimFile()
    {
        return DEF_FILE;
    }

    /**
     * @param in
     *            the InputStream, i.e. the source, closes the passed input
     *            stream
     * @param fn
     *            where to output the file (numbers are ok)
     */
    protected final void fileForShow(InputStream in, String fn)
    {
        File f = (fn == null || fn.equals("") ? DEF_FILE : getFile(fn));
        boolean fout = "true".equals(System.getProperty("file.echo"));

        try
        {
            FileOutputStream out = new FileOutputStream(f);
            int b = 0;
            while ((b = in.read()) != -1)
            {
                if (fout)
                    Console.out.write(b);
                out.write(b);
            }
            out.flush();
            out.close();
            Console.out.flush();
            in.close();
            s_showFile = f;
        }
        catch (Exception exp)
        {
            System.err.println("FAILED TO WRITE TO OUTPUT FILE " + f.getAbsolutePath());
            exp.printStackTrace();
        }
    }

    protected final void fileForShow(String fn)
    {
        if (fn == null || fn.equals(""))
        {
            return;
        }
        s_showFile = getFile(fn);
    }

    protected final void fileForShow(File fn)
    {
        if (!fn.exists() || !fn.isFile())
        {
            return;
        }
        s_showFile = fn;
    }

    protected final File getShowFile()
    {
        return s_showFile;
    }

    void setConsole(JConsole sim)
    {
        m_sim = sim;
    }

    public final String getName()
    {
        return m_name;
    }

    public final String getShortDescription()
    {
        return m_shortDescription;
    }

    public final String getHelp()
    {
        return m_help;
    }

    /** @returns a read-only list. */
    protected final List getAllCommandsList()
    {
        return m_sim.getAllCommandsList();
    }

    /** @returns a read-only map. */
    protected final Map getAllCommandsMap()
    {
        return m_sim.getAllCommandsMap();
    }

    protected final ConsoleCommand getCommand(String cmdName)
    {
        return m_sim.getCommand(cmdName);
    }

    protected final String readUserInputLine()
    {
        return m_sim.getUserInput();
    }

    protected final void throwCmdResult(String m) throws CommandResultException
    {
        throw new CommandResultException(m);
    }

    protected final void throwCmdResult(String m, int code) throws CommandResultException
    {
        throw new CommandResultException(m, code);
    }

    protected final void throwCmdResult(String m, int code, Object res) throws CommandResultException
    {
        throw new CommandResultException(m, code, res);
    }

    protected final void throwCmdFailed(String m) throws CommandFailedException
    {
        throw new CommandFailedException(m);
    }

    protected final void throwCmdFailed(String m, Exception e) throws CommandFailedException
    {
        throw new CommandFailedException(m, e);
    }

    public abstract void execute(String[] args) throws CommandFailedException;

    protected abstract void initialize() throws Exception;

    protected final File getCurrentDir()
    {
        return new File(m_sim.getCurrentDir());
    }

    protected final void setCurrentDir(String path)
    {
        m_sim.setCurrentDir(path);
    }

    protected Map getShareMap()
    {
        return null;
    }

    public Object getSharedObject(Object key)
    {
        Map m = getShareMap(); // the sub-class getShareMap() will be called
                                // (if implemented)
        if (m != null)
        {
            return m.get(key);
        }
        return null;
    }

    protected final File getFile(String aKey)
    {
        return m_sim.getFile(aKey);
    }

    public List getCommandHistory()
    {
        return m_sim.getCommandHistory();
    }

    protected final void displayPrompt(String str)
    {
        m_sim.displayPrompt(str);
    }

    protected final void displayMessage(String str)
    {
        m_sim.displayMessage(str);
    }

    public boolean getVerbose()
    {
        return m_sim.getVerbose();
    }

    protected boolean isScript(String name)
    {
        return m_sim.isScript(name);
    }

    protected String[] args(String arg1)
    {
        return new String[] { arg1 };
    }

    protected String[] args(String arg1, String args2)
    {
        return new String[] { arg1, args2 };
    }

    protected String[] args(String arg1, String args2, String args3)
    {
        return new String[] { arg1, args2, args3 };
    }

    protected void processLine(String line)
    {
        m_sim.processLine(line);
    }

}
