package vcluster.ui;

import java.io.*;

import vcluster.global.Config;
import vcluster.global.VCluster;
import vcluster.util.PrintMsg;
import vcluster.util.Util;
import vcluster.util.PrintMsg.DMsgType;

public class UIMain {


	public UIMain()
	{
		//Menu.initMenuStruct();
		VCluster.init();
		Util.loadConfig(Config.configFile);
	}
	

	
	private boolean isEmpty(String aCmd)
	{
		if (aCmd == null) return true;
		
		String cmd = aCmd.replaceAll(" ", "");
		return cmd.isEmpty();
	}

	/**
	 * generate "vcluster" prompt
	 * 
	 * @return true if it has to be continue; otherwise false
	 */
	private  boolean promptGen() {
		
	    String userCmd = "";

	    System.out.print("vcluter > ");
		InputStreamReader input = new InputStreamReader(System.in);
	    BufferedReader reader = new BufferedReader(input);
	    try {
	    	userCmd = reader.readLine(); 
	    }
	    catch(Exception e){return true;}


	    if (isEmpty(userCmd))
	    	return true;

	    Command command = CmdExecutor.getCommand(userCmd);

	    if (command == Command.QUIT) {
	    	CmdExecutor.quit();
	    	
	    	/* forcedly exit */
	    	System.exit(0);
	    }
	    
	    if (command == Command.NOT_DEFINED) {
	    	PrintMsg.print(DMsgType.ERROR, "command is not supported!");
	    	return true;
	    }
	    
	    CmdExecutor.execute(userCmd);

	    return true;
	    
	}

	public static void main(String[] args) throws Exception {

		UIMain uimain = new UIMain();

		boolean more = false;
		do {
			more = uimain.promptGen();
		}while (more == true);
	}

}
