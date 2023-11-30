package ee.pawadeck.taskmanagement.task;

import ee.pawadeck.taskmanagement.task.dto.TaskDto;
import ee.pawadeck.taskmanagement.task.mapper.TaskMapper;
import ee.pawadeck.tasks.EventType;
import ee.pawadeck.tasks.TaskEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class TaskEventProducer {

    private final KafkaTemplate<String, TaskEvent> kafkaTemplate;
    private final TaskMapper taskMapper;


    public void sendToKafka(TaskDto taskDto, EventType eventType) {
        TaskEvent taskEvent = taskMapper.toTaskEvent(taskDto);
        taskEvent.setEventType(eventType);
        kafkaTemplate.send("task-event", taskEvent);
        log.info("Task ID={} event is sent to kafka successfully", taskDto.getId());
    }

}
