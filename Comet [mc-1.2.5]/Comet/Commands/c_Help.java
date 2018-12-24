package Comet.Commands;

import java.util.Iterator;

public class c_Help extends Base_Command {

	public void run(String as[]) throws Exception {
		if(as.length < 1) {
			String s = "Avaible commands : ";
			for(Iterator iterator = Base_List.commands.keySet().iterator(); iterator.hasNext();)
			{
				String s2 = (String)iterator.next();
				s = (new StringBuilder()).append(s).append('-').append(s2).append(", ").toString();
			}

			s = s.substring(0, s.length() - 2);
			Comet.Comet.utils.addChat(s);
		} else {
			String s1 = as[0].toLowerCase();
			if(Base_List.commands.containsKey(s1))
			{
				Base_Command basecommand = (Base_Command)Base_List.commands.get(s1);
				if(basecommand == null)
				{
					throw new Exception("Unknown command! Did you mean -help?");
				}
				Comet.Comet.utils.addChat((new StringBuilder()).append('-').append(s1).append("").append(basecommand.getUsage()).toString());
				Comet.Comet.utils.addChat(basecommand.getHelp());
			} else
			{
				throw new Exception("Unknown command! Did you mean -help?");
			}
		}
	}

	public String getHelp()
	{
		return "";
	}

	public String getUsage()
	{
		return "[command]";
	}
}
