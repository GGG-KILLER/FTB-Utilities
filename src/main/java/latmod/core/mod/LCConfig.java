package latmod.core.mod;

import latmod.core.*;
import latmod.core.util.FastList;
import net.minecraft.init.*;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.config.Property;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class LCConfig extends LMConfig implements IServerConfig
{
	public static LCConfig instance;
	
	public LCConfig(FMLPreInitializationEvent e)
	{
		super(e, "/LatMod/LatCoreMC.cfg");
		instance = this;
		load();
	}
	
	public void load()
	{
		General.load(get("general"));
		Client.load(get("client"));
		Recipes.load(get("recipes"));
		save();
	}
	
	public void readConfig(NBTTagCompound tag)
	{
		General.friendsGuiEnabled = tag.getBoolean("Friends");
		General.friendsGuiArmor = tag.getBoolean("Armor");
	}
	
	public void writeConfig(NBTTagCompound tag)
	{
		tag.setBoolean("Friends", General.friendsGuiEnabled);
		tag.setBoolean("Armor", General.friendsGuiArmor);
	}
	
	public static class General
	{
		public static boolean checkUpdates;
		public static boolean disableLatCoreCommand;
		public static boolean disableCommandOverrides;
		public static boolean friendsGuiEnabled;
		public static boolean friendsGuiArmor;
		
		public static void load(Category c)
		{
			checkUpdates = c.getBool("checkUpdates", true);
			disableLatCoreCommand = c.getBool("disableLatCoreCommand", false);
			disableCommandOverrides = c.getBool("disableCommandOverrides", false);
			friendsGuiEnabled = c.getBool("friendsGuiEnabled", true);
			friendsGuiArmor = c.getBool("friendsGuiArmor", true);
		}
	}
	
	public static class Client
	{
		public static boolean enablePlayerDecorators;
		public static boolean rotateBlocks;
		public static boolean renderHighlights;
		public static boolean addAllColorItems;
		public static boolean addOreNames;
		public static boolean addRegistryNames;
		
		public static void load(Category c)
		{
			FastList<String> p = new FastList<String>();
			
			enablePlayerDecorators = c.getBool("EnablePlayerDecorators", true); p.add("EnablePlayerDecorators");
			rotateBlocks = c.getBool("RotateBlocks", true); p.add("RotateBlocks");
			renderHighlights = c.getBool("RenderHighlights", true); p.add("RenderHighlights");
			addAllColorItems = c.getBool("AddAllColorItems", true); p.add("AddAllColorItems");
			addOreNames = c.getBool("AddOreNames", false); p.add("AddOreNames");
			addRegistryNames = c.getBool("AddRegistryNames", false); p.add("AddRegistryNames");
			
			FastList<Property> l = new FastList<Property>();
			l.addAll(c.getCategory().values());
			
			for(int i = 0; i < l.size(); i++)
			{
				String p1 = l.get(i).getName();
				if(!p.contains(p1)) c.getCategory().remove(p1);
			}
		}
	}
	
	public static class Recipes
	{
		public static boolean smeltFleshToLeather;
		public static boolean craftWoolWithDye;
		
		public static void load(Category c)
		{
			smeltFleshToLeather = c.getBool("smeltFleshToLeather", true);
			craftWoolWithDye = c.getBool("craftWoolWithDye", true);
		}
		
		public static void loadRecipes()
		{
			if(smeltFleshToLeather)
				LC.mod.recipes.addSmelting(new ItemStack(Items.rotten_flesh), new ItemStack(Items.leather));
			
			if(craftWoolWithDye)
			{
				for(int i = 0; i < 16; i++)
					LC.mod.recipes.addRecipe(new ItemStack(Blocks.wool, 8, i), "WWW", "WDW", "WWW",
							'W', new ItemStack(Blocks.wool, 1, ODItems.ANY),
							'D', EnumDyeColor.VALUES[i].dyeName);
			}
		}
	}
}