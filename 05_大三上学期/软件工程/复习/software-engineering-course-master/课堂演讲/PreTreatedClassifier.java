import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

public class PreTreatedClassifier implements Classifier{
    private DecisionTreClassifier core = new DecisionTreClassifier();
    public PreTreatedClassifier(){

    }
    public static void main(String[] args) {
        Classifier test = new DecisionTreClassifier();
        Classifier proxy = (Classifier)Proxy.newProxyInstance(
            DecisionTreClassifier.class.getClassLoader(), 
            new Class[]{Classifier.class}, 
            new DecisionTreClassifierInvoke(test));
        proxy.train(null);
    }

    private List<Data> preTreated(List<Data> data){
        // 这里是数据处理内容
        // 我确信已发现了一种美妙的写法，可惜这里空白的地方太小，写不下
        System.out.println("进行预处理");
        return data;
    }

    @Override
    public void train(List<Data> data) {
        List<Data> treatedData = preTreated(data);
        core.train(treatedData);
    }

    @Override
    public Label test(Data data) {
        return core.test(data);
    }
}

class DecisionTreClassifierInvoke implements InvocationHandler{
    private Classifier classifier;

    public DecisionTreClassifierInvoke(Classifier give){
        this.classifier = give;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("进入代理");
        System.out.println(method.getName());
        method.invoke(this.classifier, new Object[]{null});
        return null;
    }
    
} 

class DecisionTreClassifier implements Classifier{

    @Override
    public void train(List<Data> data) {
        // 这里是训练模型内容
        // 我确信已发现了一种美妙的写法，可惜这里空白的地方太小，写不下
        System.out.println(data+"已训练");
    }

    @Override
    public Label test(Data data) {
        // 这里是分类判断内容
        // 我确信已发现了一种美妙的写法，可惜这里空白的地方太小，写不下
        return new Label();
    }
    
}

interface Classifier{
    //看我干啥，我啥也不是，啥也没有
    public void train(List<Data> data);
    public Label test(Data data);
}


class Data{
    int data = 0;
}
class Label{
    String label = "?";
}