package subscribers;

import com.rti.dds.infrastructure.RETCODE_NO_DATA;
import com.rti.dds.infrastructure.ResourceLimitsQosPolicy;
import com.rti.dds.subscription.*;
import gen.WindowOpen;
import gen.WindowOpenDataReader;
import gen.WindowOpenSeq;
import gen.WindowOpenTypeSupport;

public class WindowOpenSubscriber extends SubscriberBase<WindowOpenDataReader, WindowOpenSubscriber.IWindowOpener> {

    public WindowOpenSubscriber(int domainID, String topicName, IWindowOpener windowOpener) {
        super(domainID, topicName, windowOpener);
    }

    @Override
    protected void createListener() {
        listener = new WindowOpenSubscriber.WindowOpenListener(typeName, handler);
    }

    @Override
    protected void setTypeName() {
        typeName = WindowOpenTypeSupport.get_type_name();
        WindowOpenTypeSupport.register_type(participant, typeName);
    }

    public interface IWindowOpener{
        void openWindow(double intensity);
    }

    public static class WindowOpenListener extends DataReaderAdapter {

        WindowOpenSeq windowOpenSeq;
        SampleInfoSeq infoSeq;
        String typeName;
        IWindowOpener windowOpener;

        public WindowOpenListener(String typeName, IWindowOpener windowOpener) {
            this.typeName = typeName;
            windowOpenSeq = new WindowOpenSeq();
            infoSeq = new SampleInfoSeq();
            this.windowOpener = windowOpener;
        }

        public void on_data_available(DataReader reader) {
            WindowOpenDataReader windowOpenDataReader = (WindowOpenDataReader) reader;

            try {
                windowOpenDataReader.take(
                        windowOpenSeq,
                        infoSeq,
                        ResourceLimitsQosPolicy.LENGTH_UNLIMITED,
                        SampleStateKind.ANY_SAMPLE_STATE,
                        ViewStateKind.ANY_VIEW_STATE,
                        InstanceStateKind.ANY_INSTANCE_STATE);

                for (int i = 0; i < windowOpenSeq.size(); i++) {
                    SampleInfo info = infoSeq.get(i);

                    if (info.valid_data) {
                        WindowOpen data = windowOpenSeq.get(i);
                        System.out.println(typeName+" --> Intesity: " + data.intensity);
                        windowOpener.openWindow(data.intensity);
                    }
                }
            } catch (RETCODE_NO_DATA noData) {
                // No data to process
            } finally {
                windowOpenDataReader.return_loan(windowOpenSeq, infoSeq);
            }
        }
    }

}

