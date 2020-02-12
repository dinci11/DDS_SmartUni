package subscribers;

import com.rti.dds.domain.DomainParticipant;
import com.rti.dds.domain.DomainParticipantFactory;
import com.rti.dds.infrastructure.StatusKind;
import com.rti.dds.subscription.DataReaderListener;
import com.rti.dds.subscription.Subscriber;
import com.rti.dds.topic.Topic;
import com.sun.jdi.InvalidTypeException;
import gen.HeatingDataReader;

abstract public class SubscriberBase<DataReaderType, HandlerType> {

    public int domainID;
    public String typeName;

    public DomainParticipant participant;
    public Subscriber subscriber;
    public Topic topic;
    public DataReaderListener listener;
    public DataReaderType reader;
    public HandlerType handler;

    public SubscriberBase(int domainID, String topicName, HandlerType handler) {

        this.domainID = domainID;
        this.handler = handler;

        try {
            createParticipant();
            createSubscriber();
            setTypeName();
            createTopic(topicName);
            createListener();
            createReader();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void createReader() {
        if (listener == null) {
            throw new NullPointerException("Listener is null");
        }

        reader = (DataReaderType) subscriber.create_datareader(
                topic,
                Subscriber.DATAREADER_QOS_DEFAULT,
                listener,
                StatusKind.STATUS_MASK_ALL);

        if (reader == null) {
            System.err.println("create_datareader error\n");
            throw new NullPointerException("Reader is null");
        }
    }

    protected abstract void createListener();

    private void createTopic(String topicName) throws InvalidTypeException {
        if (typeName.isEmpty()) {
            throw new InvalidTypeException("typeName cannot be empty");
        }

        topic = participant.create_topic(
                topicName,
                typeName,
                DomainParticipant.TOPIC_QOS_DEFAULT,
                null,
                StatusKind.STATUS_MASK_NONE);

        if (topic == null) {
            System.err.println("create_topic error\n");
            throw new NullPointerException("Topic is null");
        }
    }

    protected abstract void setTypeName();

    private void createSubscriber() {
        subscriber = participant.create_subscriber(
                DomainParticipant.SUBSCRIBER_QOS_DEFAULT,
                null,
                StatusKind.STATUS_MASK_NONE);

        if (subscriber == null) {
            System.err.println("create_subscriber error\n");
            throw new NullPointerException("Subscriber is null");
        }
    }

    private void createParticipant() {
        participant = DomainParticipantFactory.TheParticipantFactory.create_participant(
                domainID,
                DomainParticipantFactory.PARTICIPANT_QOS_DEFAULT,
                null,
                StatusKind.STATUS_MASK_NONE);

        if (participant == null) {
            System.err.println("create_participant error\n");
            throw new NullPointerException("Participant is null");
        }
    }

    public void ShutDownSubscriber() {

        if (participant != null) {
            participant.delete_contained_entities();

            DomainParticipantFactory.TheParticipantFactory.
                    delete_participant(participant);
        }
    }

}
