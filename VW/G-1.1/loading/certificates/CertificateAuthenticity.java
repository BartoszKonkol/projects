package vw.loading.certificates;

import java.math.BigInteger;
import javax.jnf.exception.Defect;
import javax.jnf.importation.Certificate;
import vw.util.DataTexts;

public final class CertificateAuthenticity extends Certificate
{
	
	public CertificateAuthenticity()
	{
		
		super(
				CertificateTypes.AUTHENTICITY,
				new BigInteger("36754996813893148635"),
				new String[] {"\u00A9 Bartosz Konkol"},
				new String[] {DataTexts.nameVW},
				CertificateFunctions.AUTHENTICITY_ECERTIFICATE
			);
		
	}
	
	@Override
	protected final void setStart()
	{
		
		System.out.println("Certyfikat Autentyczno\u015bci modyfikacji " + DataTexts.versionVW);
		
	}
	
	@Override
	protected final void setError()
	{
		
		System.err.println("Na skutek awarii, gra zostanie zamkni\u0119ta.");
		
		try
		{
			
			throw new Defect("Certyfikat Autentyczno\u015bci nie istnieje lub jest b\u0142\u0119dny!");
			
		}
		catch (final Defect e)
		{
			
			System.err.println(e.toString());
			e.exit();
			
		}
		
	}
	
	protected static final void error()
	{
		
		System.err.println();
		System.err.println("B\u0142\u0105d podczas u\u017cywania certyfikatu biblioteki JNF.");
		System.err.println("Na skutek awarii, gra zostanie zamkni\u0119ta.");
		
		try
		{
			
			throw new Defect("Certyfikat Autentyczno\u015bci nie istnieje lub jest b\u0142\u0119dny!");
			
		}
		catch (final Defect e)
		{
			
			System.err.println(e.toString());
			e.exit();
			
		}
		
	}
	
}
