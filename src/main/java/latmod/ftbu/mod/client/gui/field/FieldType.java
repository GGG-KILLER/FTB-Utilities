package latmod.ftbu.mod.client.gui.field;

public enum FieldType
{
	TEXT,
	INT,
	FLOAT;
	
	public boolean isCharValid(char c)
	{
		if(this == TEXT || c == '-') return true;
		else if(c == '.') return this == FLOAT;
		else return c >= '0' && c <= '9';
	}
}