package model;

import java.util.Collection;

import model.ircevent.*;

public class Parser {
	public static IRCEvent parse(String raw)
	{
		String t[] = raw.split(":");
		if (t[1].contains("@"))
		{
			if (t[1].split(" ")[1].equals("PRIVMSG"))
			{
				String user = t[1].split("!")[0];
				String channel = t[1].split(" ")[2];
				String msg = t[2];
				return new PrivmsgEvent(channel, msg, user);
			}
			else if (t[1].split(" ")[1].equals("JOIN"))
			{
				String channel = t[1].split(" ")[2];
				String user = t[1].split("!")[0];
				return new JoinEvent(channel,user);
			}
			else if (t[1].split(" ")[1].equals("PART"))
			{
				String channel = t[1].split(" ")[2];
				String user = t[1].split("!")[0];
				return new PartEvent(channel,user);
			}
//	:asdasdasd_!webchat@user-109-243-23-184.play-internet.pl TOPIC #e-sim.bt :Simple Topic
			else if (t[1].split(" ")[1].equals("TOPIC"))
			{
				String channel = t[1].split(" ")[2];
				String user = t[1].split("!")[0];
				String msg = t[2];
				return new TopicChangeEvent(user,channel,msg);
			}
		}
		//:underworld1.no.quakenet.org 353 ArP = #Nad_Romantycznym_Ruczajem :ArP @Ukar Focuus Bzzzyk DmoszkuGTX Brenia Mrowa CzarodziejKamil FrankZappa Randomowiec Slannesh BratPL chinczyk666 Vasu_ FxK Opi RanchoCucamonga ISO9001 PieknyRoman Bachu MrAlpaka cbool22 +RmX Kurczaki +Jaa Rev| Ender4K TechnoTampon +Maverick91 @CyfrowyDante kasper93 xnt chrom64 kobitka panredaktor ImQ009 Silwanos Miksuss Kamazjest +Sesus_exe Acardul ast @negocki Radio-Erewan_ @B4rt0[off] +Normalny[w] Dexior BOT_Poorchat
		else
		{
			if (t[1].split(" ")[1].equals("353"))//names event
			{
				String channel = t[1].split(" ")[4];
				String n[] = t[2].split(" ");
				NamesEvent ne = new NamesEvent(channel);
				for (int i = 0; i < n.length; i++)
					ne.getNicks().add(new User(n[i]));
				return ne;
			}
//	:servercentral.il.us.quakenet.org 332 ArP #e-sim.bt :Simple Topic
			else if (t[1].split(" ")[1].equals("332"))
			{
				String channel = t[1].split(" ")[3];
				return new TopicEvent(channel,t[2]);
			}
		}
		return new RAWEvent(raw);
	}
}
