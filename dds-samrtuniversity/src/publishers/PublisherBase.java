package publishers;

import com.rti.dds.domain.DomainParticipant;
import com.rti.dds.domain.DomainParticipantFactory;
import com.rti.dds.infrastructure.StatusKind;
import com.rti.dds.publication.Publisher;
import com.rti.dds.topic.Topic;
import com.sun.jdi.InvalidTypeException;
import gen.HeatingDataWriter;

public abstract class PublisherBase<WriterType> {

    public int domainID;
    public String typeName;

    public DomainParticipant participant;
    public Publisher publisher;
    public Topic topic;
    public WriterType writer;

    public PublisherBase(int domainID,String topicName) {
        this.domainID = domainID;

        try {
            createParticipant();
            createPublisher();
            setTypeName();
            createTopic(topicName);
            createWriter();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void createWriter() throws NullPointerException {
        writer = (WriterType) publisher.create_datawriter(
                topic,
                Publisher.DATAWRITER_QOS_DEFAULT,
                null,
                StatusKind.STATUS_MASK_NONE);

        if (writer == null) {
            System.err.println("create_datawriter error\n");
            throw new NullPointerException("Writer is null");
        }
    }

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

    private void createParticipant() throws NullPointerException {
        participant = DomainParticipantFactory.TheParticipantFactory.
                create_participant(
                        domainID,
                        DomainParticipantFactory.PARTICIPANT_QOS_DEFAULT,
                        null,
                        StatusKind.STATUS_MASK_NONE);

        if (participant == null) {
            System.err.println("create_participant error\n");
            throw new NullPointerException("Participant is null");
        }
    }

    private void createPublisher() throws NullPointerException {
        publisher = participant.create_publisher(
                DomainParticipant.PUBLISHER_QOS_DEFAULT,
                null,
                StatusKind.STATUS_MASK_NONE);

        if (publisher == null) {
            System.err.println("create_publisher error\n");
            throw new NullPointerException("Publisher is null");
        }
    }

    abstract protected void setTypeName();

    public void ShutDownPublisher() {

        if (participant != null) {
            participant.delete_contained_entities();

            DomainParticipantFactory.TheParticipantFactory.
                    delete_participant(participant);
        }
    }
}
