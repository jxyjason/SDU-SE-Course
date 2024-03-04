import com.formdev.flatlaf.FlatLightLaf;

public class theme1
	extends FlatLightLaf
{
	public static final String NAME = "theme1";

	public static boolean setup() {
		return setup( new theme1() );
	}

	public static void installLafInfo() {
		installLafInfo( NAME, theme1.class );
	}

	@Override
	public String getName() {
		return NAME;
	}
}
