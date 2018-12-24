package Comet.Utils;

public enum Enum_Options
{
	Opacity("Opacity", true, false),
	MoveSpeed("Opacity", true, false),
	StepHeight("Opacity", true, false),
	ReachDistance("Opacity", true, false),
	Zoom("Opacity", true, false),
	FlySpeed("Opacity", true, false),
	JumpHeight("Opacity", true, false);
	

	private final boolean enumFloat;
	private final boolean enumBoolean;
	private final String enumString;

	public static Enum_Options getEnumOptions(int par0)
	{
		Enum_Options aenumoptions[] = values();
		int i = aenumoptions.length;

		for (int j = 0; j < i; j++)
		{
			Enum_Options enumoptions = aenumoptions[j];

			if (enumoptions.returnEnumOrdinal() == par0)
			{
				return enumoptions;
			}
		}

		return null;
	}

	private Enum_Options(String par3Str, boolean par4, boolean par5)
	{
		enumString = par3Str;
		enumFloat = par4;
		enumBoolean = par5;
	}

	public boolean getEnumFloat()
	{
		return enumFloat;
	}

	public boolean getEnumBoolean()
	{
		return enumBoolean;
	}

	public int returnEnumOrdinal()
	{
		return ordinal();
	}

	public String getEnumString()
	{
		return enumString;
	}
}
