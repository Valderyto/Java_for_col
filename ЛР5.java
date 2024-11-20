import java.util.ArrayList;
import java.util.List;

// Интерфейс жизненного цикла
interface Lifecycle {
    void onStart();
    void onPause();
    void onStop();
    void onResume();
    void onDestroy();
}

// Абстрактный класс Application
abstract class Application {
    String name;
    String version;
    String developer;
    boolean isRunning;

    public Application(String name, String version, String developer) {
        this.name = name;
        this.version = version;
        this.developer = developer;
        this.isRunning = false;
    }

    public void onStart() {
        isRunning = true;
        System.out.println(name + " запущено.");
    }

    public void onClose() {
        isRunning = false;
        System.out.println(name + " закрыто.");
    }
}

// Абстрактный класс Activity
abstract class Activity extends Application implements Lifecycle {
    String activityName;
    boolean isCreated;
    boolean isVisible;
    List<Fragment> fragments;

    public Activity(String name, String version, String developer) {
        super(name, version, developer);
        this.activityName = name;
        this.fragments = new ArrayList<>();
        this.isCreated = false;
        this.isVisible = false;
    }

    @Override
    public void onStart() {
        System.out.println("Activity " + activityName + " запущена.");
        this.isCreated = true;
    }

    public Activity onCreate(Fragment fragment) {
        fragments.add(fragment);
        System.out.println("Фрагмент " + fragment.getName() + " инициализирован.");
        return this;
    }

    @Override
    public void onPause() {
        System.out.println("Activity " + activityName + " на паузе.");
        this.isVisible = false;
    }

    @Override
    public void onStop() {
        System.out.println("Activity " + activityName + " остановлена.");
    }

    @Override
    public void onResume() {
        System.out.println("Activity " + activityName + " возобновлена.");
        this.isVisible = true;
    }

    @Override
    public void onDestroy() {
        System.out.println("Activity " + activityName + " уничтожена.");
        this.isCreated = false;
    }
}

// Абстрактный класс Fragment
abstract class Fragment implements Lifecycle {
    String fragmentName;
    Activity activity;
    boolean isViewCreated;
    
    public Fragment(String name) {
        this.fragmentName = name;
        this.isViewCreated = false;
    }

    public String getName() {
        return fragmentName;
    }

    @Override
    public void onStart() {
        System.out.println("Фрагмент " + fragmentName + " запущен.");
    }

    public void onAttach(Activity activity) {
        this.activity = activity;
        System.out.println("Фрагмент " + fragmentName + " прикреплен к Activity " + activity.activityName);
    }

    public void onCreateView() {
        System.out.println("Представление для фрагмента " + fragmentName + " создается.");
        this.isViewCreated = true;
    }

    public void onViewCreated() {
        if (isViewCreated) {
            System.out.println("Представление для фрагмента " + fragmentName + " создано.");
        }
    }

    public void onDestroyView() {
        if (isViewCreated) {
            System.out.println("Представление для фрагмента " + fragmentName + " уничтожено.");
            this.isViewCreated = false;
        }
    }

    @Override
    public void onPause() {
        System.out.println("Фрагмент " + fragmentName + " на паузе.");
    }

    @Override
    public void onStop() {
        System.out.println("Фрагмент " + fragmentName + " остановлен.");
    }

    @Override
    public void onResume() {
        System.out.println("Фрагмент " + fragmentName + " возобновлен.");
    }

    public void onDetach() {
        System.out.println("Фрагмент " + fragmentName + " откреплен от Activity.");
        this.activity = null;
    }

    @Override
    public void onDestroy() {
        System.out.println("Фрагмент " + fragmentName + " уничтожен.");
    }
}
// Класс DialogFragment, расширяющий Fragment и реализующий onStart()
class DialogFragment extends Fragment {
    public DialogFragment(String name) {
        super(name);
    }

    @Override
    public void onStart() {
        System.out.println("DialogFragment " + fragmentName + " запущен.");
    }

    public void showDialog(Fragment fragment) {
        fragment.onPause();
        System.out.println("Показан DialogFragment " + fragmentName + ", фрагмент " + fragment.getName() + " на паузе.");
    }

    public void closeDialog(Fragment fragment) {
        System.out.println("DialogFragment " + fragmentName + " закрыт, фрагмент " + fragment.getName() + " возобновлен.");
        fragment.onResume();
    }
}

// Интерфейс передачи данных
interface DataTransferInterface {
    void sendData(String data);
}

// Класс MainFragment, реализующий DataTransferInterface и onStart()
class MainFragment extends Fragment implements DataTransferInterface {
    public MainFragment(String name) {
        super(name);
    }

    @Override
    public void onStart() {
        System.out.println("MainFragment " + fragmentName + " запущен.");
    }

    @Override
    public void sendData(String data) {
        System.out.println("MainFragment получил данные: " + data);
    }
}

// Класс CustomDialogFragment, расширяющий DialogFragment и передающий данные
class CustomDialogFragment extends DialogFragment {
    DataTransferInterface dataTransferInterface;

    public CustomDialogFragment(String name, DataTransferInterface dataTransferInterface) {
        super(name);
        this.dataTransferInterface = dataTransferInterface;
    }

    public void sendDataToFragment(String data) {
        if (dataTransferInterface != null) {
            dataTransferInterface.sendData(data);
        }
    }
}

// Демонстрация работы системы
public class Main {
    public static void main(String[] args) {
        Activity mainActivity = new Activity("MainActivity", "1.0", "DeveloperName") {
            @Override
            public void onStart() {
                super.onStart();
            }
        };
        
        Fragment mainFragment = new MainFragment("MainFragment");
        DialogFragment dialogFragment = new CustomDialogFragment("CustomDialogFragment", (DataTransferInterface) mainFragment);

        mainActivity.onStart();
        mainActivity.onCreate(mainFragment);

        mainFragment.onAttach(mainActivity);
        mainFragment.onCreateView();
        mainFragment.onViewCreated();

        dialogFragment.onAttach(mainActivity);
        dialogFragment.showDialog(mainFragment);

        ((CustomDialogFragment) dialogFragment).sendDataToFragment("Hello from Dialog!");

        dialogFragment.closeDialog(mainFragment);
        mainFragment.onDetach();
        mainActivity.onDestroy();
    }
}