import oxy.bascenario.Base;
import oxy.bascenario.api.BaseApi;

public class MainTest {
    public static void main(String[] args) {
        new Base();
        BaseApi.instance().init();
    }
}
