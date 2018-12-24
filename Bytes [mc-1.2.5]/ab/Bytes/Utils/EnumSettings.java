package ab.Bytes.Utils;

public enum EnumSettings {
	SPEED("Speed", true, false);

	private final boolean enumFloat;
	private final boolean enumBoolean;
	private final String enumString;

	public static EnumSettings getEnumOptions(int par0)
	{
		EnumSettings aenumoptions[] = values();
		int i = aenumoptions.length;

		for (int j = 0; j < i; j++)
		{
			EnumSettings enumoptions = aenumoptions[j];

			if (enumoptions.returnEnumOrdinal() == par0)
			{
				return enumoptions;
			}
		}

		return null;
	}

	private EnumSettings(String par3Str, boolean par4, boolean par5)
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
