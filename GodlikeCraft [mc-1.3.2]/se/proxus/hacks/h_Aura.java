package se.proxus.hacks;

import net.minecraft.src.*;

public class h_Aura extends Base_Hack {

	private long CUR_TIME_DELAY = 0L, LAST_ATTACK_MS = 71L, LAST_ATTACK_TIME = -1L;
	private int CUR_AIMBOT_MODE = 2;
	private boolean IS_CRITS_ON = false, IS_AURA_RUNNING = false;

	public h_Aura() {
		super('4', "Kill_aura", "Makes you hit people.", "Aura", "F");
		this.setOption(Base_Options.KILL_AURA_DISTANCE, Float.valueOf(4.5F));
		this.setOption(Base_Options.KILL_AURA_DELAY, Long.valueOf(67L));
	}

	@Override
	public void onEnabled() {

	}

	@Override
	public void onDisabled() {

	}

	@Override
	public void onToggled() {

	}

	@Override
	public void onUpdate() {
		if(getState()) {
			CUR_AIMBOT_MODE = this.vars.aimbotMode;
			IS_CRITS_ON = this.hacks.crits.getState();
			CUR_TIME_DELAY = System.nanoTime() / 1000000;

			for(Object o : mc.theWorld.playerEntities) {
				EntityPlayerSP ep = mc.thePlayer;
				EntityPlayer e = (EntityPlayer)o;
				
				if(e != ep && e != null && ep != null && this.getNearestEntityPlayer() != null) {
					if(this.utils.isFriend(this.getNearestEntityPlayer().username)) {
						break;
					}
					
					if(ep.getDistanceToEntity(getNearestEntityPlayer()) <= ((Float)getOption(Base_Options.KILL_AURA_DISTANCE)).floatValue() && ep.canEntityBeSeen(getNearestEntityPlayer())) {
						if(e.hurtTime <= 0 && (CUR_TIME_DELAY - LAST_ATTACK_TIME >= ((Long)getOption(Base_Options.KILL_AURA_DELAY)).longValue() || LAST_ATTACK_TIME == -1)) {
							IS_AURA_RUNNING = true;
							
							if(this.hacks.autoTool.getState()) {
								getBestWeapon(e);
							}
							
							silentAimbot(getNearestEntityPlayer());
							ep.swingItem();	

							if(this.mc.thePlayer.getCurrentEquippedItem() != null && getModBoolean()[2]) {
								if(this.isSword(this.mc.thePlayer.getCurrentEquippedItem().getItem())) {
									this.mc.thePlayer.getCurrentEquippedItem().useItemRightClick(this.mc.theWorld, this.mc.thePlayer);
								}
							}

							mc.playerController.attackEntity(ep, getNearestEntityPlayer());
							LAST_ATTACK_TIME = System.nanoTime() / 1000000;
							break;
						}
					} else {
						IS_AURA_RUNNING = false;
					}
				}
			}
		}
	}

	@Override
	public String[] getModString() {
		return (new String[] {});
	}

	@Override
	public int[] getModInteger() {
		return (new int[] {CUR_AIMBOT_MODE});
	}

	@Override
	public float[] getModFloat() {
		return (new float[] {});
	}

	@Override
	public long[] getModLong() {
		return (new long[] {CUR_TIME_DELAY, LAST_ATTACK_TIME});
	}

	@Override
	public boolean[] getModBoolean() {
		return (new boolean[] {IS_CRITS_ON, IS_AURA_RUNNING, this.vars.isAuraBlocking});
	}

	private void silentAimbot(Entity e) {
		if(!(this.utils.isFriend(e.getEntityName()))) {
			if(getModInteger()[0] == 1) {
				EntityPlayerSP tp = mc.thePlayer;
				double x = e.posX - tp.posX, y = e.posY - tp.posY, z = e.posZ - tp.posZ;
				double var1 = (tp.posY + (double)tp.getEyeHeight()) - (e.posY + (double)e.getEyeHeight()), var2 = MathHelper.sqrt_double(x * x + z * z);
				float var3 = (float)((Math.atan2(z, x) * 180D) / Math.PI) - 90F, pitch = (float)(((Math.atan2(var1, var2) * 180D) / Math.PI));             
				double var4 = tp.boundingBox.minY - (tp.onGround ? 0.0001D : 0.0D);

				utils.sendPacket((new Packet13PlayerLookMove(tp.posX, var4, tp.posY, tp.posZ, var3, pitch, tp.onGround)));
			} else if(getModInteger()[0] == 2) {
				EntityPlayerSP tp = mc.thePlayer;
				tp.faceEntity(e, 360F, 360F);
			}
		}
	}

	private EntityPlayer getNearestEntityPlayer() {
		EntityPlayer nearest = null;
		if(mc.theWorld == null) {
			return null;
		} else {
			for(Object o : mc.theWorld.playerEntities) {
				if(o != null && !(o instanceof EntityPlayerSP)) {
					EntityPlayer e = (EntityPlayer)o;
					if(!e.isDead) {
						if(nearest == null) {
							nearest = e;
						} else if(nearest.getDistanceToEntity(mc.thePlayer) > e.getDistanceToEntity(mc.thePlayer)) {
							nearest = e;
						}
					}
				}
			}
			return nearest;
		}
	}

	private void getBestWeapon(Entity e) {
		EntityPlayerSP entityplayersp = this.mc.thePlayer;
		InventoryPlayer inventoryplayer = entityplayersp.inventory;
		int i = 1;
		int item = -1;

		for (int holder = 0; holder < 9; holder++) {
			ItemStack itemstack = inventoryplayer.mainInventory[holder];

			if (itemstack == null) {
				continue;
			}

			int dmgVsEntity = itemstack.getDamageVsEntity(e);

			if (dmgVsEntity > i) {
				item = holder;
				i = dmgVsEntity;
			}
		}

		if (!(item < 0)) {
			this.mc.thePlayer.inventory.currentItem = item;
			this.utils.sendPacket(new Packet16BlockItemSwitch(item));
			return;
		}
	}

	private boolean isSword(Item var1) {
		return var1.equals(Item.swordWood) || var1.equals(Item.swordStone) || var1.equals(Item.swordSteel) || var1.equals(Item.swordGold) || var1.equals(Item.swordDiamond);
	}
}