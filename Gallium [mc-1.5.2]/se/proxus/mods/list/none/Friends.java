package se.proxus.mods.list.none;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;

import net.minecraft.src.Packet3Chat;
import net.minecraft.src.Packet7UseEntity;
import net.minecraft.src.StringUtils;
import se.proxus.Gallium;
import se.proxus.commands.Command;
import se.proxus.commands.CommandType;
import se.proxus.events.EventHandler;
import se.proxus.events.list.client.EventRenderString;
import se.proxus.events.list.server.EventPacketSent;
import se.proxus.mods.Mod;
import se.proxus.mods.ModCategory;
import se.proxus.tools.ArrayHelper;
import se.proxus.tools.Colours;

public class Friends extends Mod {

	private final static List<ArrayHelper<String, String>> LOADED_FRIENDS = new LinkedList<ArrayHelper<String, String>>();

	public Friends(final Gallium client) {
		super("Friends", ModCategory.NONE, true, client);
	}

	@Override
	public void init() {
		loadFriends();
		setDescription("Handles the friends.");
		setState(true);
		getClient().getCommands()
		.registerCommand(
				new Command("friends", ".friends <add> [name] <alias>",
						"Adds a friend.", getClient(),
						CommandType.LISTHANDLER) {
					@Override
					public void onCommand(final String message,
							final String... args) {
						if (args[0].equalsIgnoreCase("add")) {
							String name = args[1];
							String alias = message
									.substring(getCommand().length()
											+ args[0].length()
											+ args[1].length() + 3);
							if (!isFriend(name)) {
								getLoadedFriends()
								.add(new ArrayHelper(args[1],
										alias));
								getClient().getPlayer().addMessage(
										Colours.GREY + "Added '"
												+ Colours.DARK_AQUA
												+ alias + Colours.GREY
												+ "'.");
								saveFriends();
							} else
								getClient()
								.getPlayer()
								.addMessage(
										Colours.GREY
										+ "You already have that user added.");
						}
					}
				});
		getClient().getCommands().registerCommand(
				new Command("friends", ".friends <remove> [name]",
						"Removes a friend.", getClient(),
						CommandType.LISTHANDLER) {
					@Override
					public void onCommand(final String message,
							final String... args) {
						if (args[0].equalsIgnoreCase("remove")) {
							String name = args[1];
							String alias = getFriendByName(name).getValue();
							if (isFriend(name)) {
								getClient().getPlayer().addMessage(
										Colours.GREY + "Removed '"
												+ Colours.DARK_AQUA + alias
												+ Colours.GREY + "'.");
								removeFriendByName(name);
								saveFriends();
							} else
								getClient()
								.getPlayer()
								.addMessage(
										Colours.GREY
										+ "You don't have that user added.");
						}
					}
				});
		getClient().getCommands().registerCommand(
				new Command("friends", ".friends <clear>",
						"Clears all your friends.", getClient(),
						CommandType.LISTHANDLER) {
					@Override
					public void onCommand(final String message,
							final String... args) {
						if (args[0].equalsIgnoreCase("clear")) {
							getClient().getPlayer().addMessage(
									Colours.GREY + "Removed '"
											+ Colours.CLIENT_COLOUR
											+ getLoadedFriends().size()
											+ Colours.GREY + "' friends.");
							getLoadedFriends().clear();
							saveFriends();
						}
					}
				});
		getClient().getCommands().registerCommand(
				new Command("friends", ".friends <list>",
						"Lists all your friends.", getClient(),
						CommandType.LISTHANDLER) {
					@Override
					public void onCommand(final String message,
							final String... args) {
						if (args[0].equalsIgnoreCase("list"))
							if (getLoadedFriends().size() > 0)
								for (ArrayHelper<String, String> friends : getLoadedFriends())
									getClient().getPlayer().addMessage(
											"Åò0Åò1Åò2Åò3Åò4Åò5Åò6Åò7Åò8Åò9ÅòyÅòr"
													+ Colours.GREY
													+ friends.getKey()
													+ " --> "
													+ Colours.DARK_AQUA
													+ friends.getValue());
							else
								getClient().getPlayer().addMessage(
										Colours.GREY + "You have no friends..");
					}
				});
	}

	@Override
	public void onEnable() {
		getClient().getEvents().registerListener(this);
	}

	@Override
	public void onDisable() {
		getClient().getEvents().unregisterListener(this);
	}

	@EventHandler
	public void onEventRenderString(final EventRenderString event) {
		if (event.getText() == null)
			return;
		for (ArrayHelper<String, String> friends : getLoadedFriends())
			if (!event.getText().startsWith("Åò0Åò1Åò2Åò3Åò4Åò5Åò6Åò7Åò8Åò9ÅòyÅòr"))
				event.setText(event.getText().replace(friends.getKey(),
						Colours.DARK_AQUA + friends.getValue() + Colours.RESET));
	}

	@EventHandler
	public void onEventPacketSent(final EventPacketSent event) {
		if (event.getPacket() instanceof Packet3Chat) {
			Packet3Chat packet = (Packet3Chat) event.getPacket();
			for (ArrayHelper<String, String> friends : getLoadedFriends())
				packet.message = packet.message.replace(friends.getValue(),
						friends.getKey());
		}
		if (event.getPacket() instanceof Packet7UseEntity) {
			Packet7UseEntity packet = (Packet7UseEntity) event.getPacket();
			event.setCancelled(isFriend(getClient().getMinecraft().theWorld
					.getEntityByID(packet.targetEntity).getEntityName()));
		}
	}

	public boolean isFriend(final String name) {
		for (ArrayHelper<String, String> friends : getLoadedFriends())
			if (friends.getKey().equalsIgnoreCase(
					StringUtils.stripControlCodes(name)))
				return true;
		return false;
	}

	public void removeFriendByName(final String name) {
		for (ArrayHelper<String, String> friends : getLoadedFriends())
			if (friends.getKey().equalsIgnoreCase(
					StringUtils.stripControlCodes(name)))
				getLoadedFriends().remove(friends);
	}

	public ArrayHelper<String, String> getFriendByName(final String name) {
		for (ArrayHelper<String, String> friends : getLoadedFriends())
			if (friends.getKey().equalsIgnoreCase(
					StringUtils.stripControlCodes(name)))
				return friends;
		return null;
	}

	public void loadFriends() {
		File file = new File(getClient().getDirectory(), "Friends.gcfg");
		String line = "";
		if (file.exists()) {
			BufferedReader fileReader;
			try {
				fileReader = new BufferedReader(new FileReader(file));
				try {
					while ((line = fileReader.readLine()) != null) {
						String[] split = line.split(" --> ");
						String name = split[0];
						String alias = split[1];
						getLoadedFriends().add(new ArrayHelper(name, alias));
						getClient().getLogger().log(
								Level.INFO,
								"Loaded the friend '" + name + ":" + alias
								+ "'.");
					}
					fileReader.close();
				} catch (Exception exception) {
					exception.printStackTrace();
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		} else
			saveFriends();
	}

	public void saveFriends() {
		try {
			File file = new File(getClient().getDirectory(), "Friends.gcfg");
			PrintWriter fileWriter = new PrintWriter(file);
			for (ArrayHelper<String, String> friends : getLoadedFriends())
				fileWriter.println(friends.getKey() + " --> "
						+ friends.getValue());
			fileWriter.close();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	public static List<ArrayHelper<String, String>> getLoadedFriends() {
		return LOADED_FRIENDS;
	}
}