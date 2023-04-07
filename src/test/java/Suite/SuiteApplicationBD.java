package Suite;


import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectPackages({"QueryExecutorTests", "ServiceTests"})
public class SuiteApplicationBD {
}
