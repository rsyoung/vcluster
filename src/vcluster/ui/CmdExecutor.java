package vcluster.ui;

import java.util.StringTokenizer;

import vcluster.engine.groupexecutor.CloudExecutor;
import vcluster.engine.groupexecutor.ProxyExecutor;
import vcluster.engine.groupexecutor.VClusterExecutor;
import vcluster.global.Config;

public class CmdExecutor {

	public static void quit()
	{
		/* shutdown Manager first */
		if (Config.monMan != null) Config.monMan.shutDwon();
		if (Config.vmMan != null) Config.vmMan.shutDwon();
	}

	public static boolean isQuit(String aCmd)
	{
		String cmd = aCmd.trim();
		if(Command.QUIT.contains(cmd)) {

			/* shutdown Manager first */
			if (Config.monMan != null) Config.monMan.shutDwon();
			if (Config.vmMan != null) Config.vmMan.shutDwon();
			
			return true;
		}

		return false;
	}
	
	public static boolean execute(String cmdLine)
	{
		StringTokenizer st = new StringTokenizer(cmdLine);
		
		String cmd = st.nextToken().trim();
		
		Command command = getCommand(cmd);
		
		if (command == Command.NOT_DEFINED) return false;
		
		switch (command.getCmdGroup()) {
		case VCLUSTER: return executeVcluster(command, cmdLine);
		case CLOUD: return executeCloud(command, cmdLine);
		case PROXY_SERVER: return executeProxy(command, cmdLine);
		}
		
		return false;
	}
	
	private static boolean executeVcluster(Command command, String cmdLine)
	{
		
		switch (command) {
		case DEBUG_MODE:
			return VClusterExecutor.debug_mode(cmdLine);
		case VMMAN:
			return VClusterExecutor.vmman(cmdLine);
		case MONITOR:
			return VClusterExecutor.monitor(cmdLine);
		case CLOUDMAN:
			return VClusterExecutor.cloudman(cmdLine);
		case SHOW:
			return VClusterExecutor.show(cmdLine);
		case LOAD:
			return VClusterExecutor.load(cmdLine);
		case SET:
			return VClusterExecutor.set(cmdLine);
		case ENGMODE:
			return VClusterExecutor.engmode(cmdLine);
		}
		
		return true;
	}
	

	private static boolean executeProxy(Command command, String cmdLine)
	{
		// command.toPrint();
		
		switch (command) {
		case CHECK_POOL: return ProxyExecutor.check_pool();
		case CHECK_Q: return ProxyExecutor.check_q();
		case CONDOR: return ProxyExecutor.condor(cmdLine);
		case ONEVM: return ProxyExecutor.onevm(cmdLine);
		}
		
		return true;
	}
	
	

	private static boolean executeCloud(Command command, String cmdLine)
	{

		/*
		RUN_INSTANCE (CMD_GROUP.CLOUD, "RunInstances, runinstance, ri, runinst, runins, run"),
		START_INSTANCE (CMD_GROUP.CLOUD, "StartInstances, startinstance, si, startinst, startins, start"),
		STOP_INSTANCE (CMD_GROUP.CLOUD, "StopInstances, stopinstance, stop"),
		DESCRIBE_INSTANCE (CMD_GROUP.CLOUD, "DescribeInstances, describeinstance, din, dins, descinst, descins"),
		TERMINATE_INSTANCE (CMD_GROUP.CLOUD, "TerminateInstances, terminateinstance, terminate, ti, kill, killins"),
		DESCRIBE_IMAGE (CMD_GROUP.CLOUD, "DescribeImages, describeimage, dim, dimg, descimg"),
		*/
		
		// command.toPrint();
		
		/* first check if a vm can be launched using cloud API
		 * if so, call registered function (plug-in based).
		 * if not, call REST API for a specified cloud system,
		 * which is chosen from cloud system pool based on priority.
		 */
		switch (command) {
		case RUN_INSTANCE: return CloudExecutor.run_instance(Config.cloudMan.getCurrentCloud());
		case DESCRIBE_INSTANCE: return CloudExecutor.describe_instance(Config.cloudMan.getCurrentCloud(), cmdLine);
		case TERMINATE_INSTANCE: return CloudExecutor.terminate_instance(Config.cloudMan.getCurrentCloud(), cmdLine);

		}
		
		return true;
	}
	
	public static Command getCommand(String aCmdLine) 
	{
		StringTokenizer st = new StringTokenizer(aCmdLine);
		String aCmd = st.nextToken().trim();
		
        for (Command cmd : Command.values())
        	if (cmd.contains(aCmd)) return cmd;
        
        return Command.NOT_DEFINED;
 	}
}
