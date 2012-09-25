package exp.rc2jsf.security;

import java.security.Principal;

import javax.security.auth.login.LoginContext;

import org.jboss.security.SimpleGroup;

import br.gov.frameworkdemoiselle.security.Authorizer;
import br.gov.frameworkdemoiselle.util.Beans;

public class JAASAuthorizer implements Authorizer {

	private static final long serialVersionUID = 1L;

	@Override
	public boolean hasRole(String role) {
		boolean result = false;

		LoginContext loginContext = Beans.getReference(JAASAuthenticator.class).getLoginContext();
		SimpleGroup group;
		Principal member;

		for (Principal principal : loginContext.getSubject().getPrincipals()) {
			if (principal instanceof SimpleGroup) {
				group = (SimpleGroup) principal;

				while (group.members().hasMoreElements()) {
					member = (Principal) group.members().nextElement();

					if (member.getName().equals(role)) {
						result = true;
						break;
					}
				}

				principal = (SimpleGroup) principal;
			}
		}

		return result;
	}

	@Override
	public boolean hasPermission(String resource, String operation) {
		return true;
	}
}
