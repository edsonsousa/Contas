import org.jboss.security.auth.spi.Util;

public class ContasUtil {

	public static String gerarSenhaHash(String senhaPlana){
		return Util.createPasswordHash("SHA-256", "BASE64", null, null, senhaPlana);
	}
	
	public static String ajustaChave(String chave) {

		
		while(chave.length() < 8){
			chave = "0".concat(chave);
		}
		

		return chave.toUpperCase();
	}
	
	public static void main(String[] args) {
		System.out.println(new ContasUtil().gerarSenhaHash("edson"));
	}
}
