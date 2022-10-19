package kr.nanoit.old.main;

import kr.nanoit.model.message.MessageDto;
import kr.nanoit.model.message.MessageType;
import kr.nanoit.old.exception.message.DeleteException;
import kr.nanoit.old.exception.message.InsertException;
import kr.nanoit.repository.ReceivedMessageRepository;
import kr.nanoit.extension.PathUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.Properties;

public class Test {
    public static void main(String[] args) throws IOException, InsertException, DeleteException {
        Properties properties = new Properties();
        properties.load(Files.newInputStream(Paths.get(PathUtils.dataBaseProperties())));
        ReceivedMessageRepository receivedMessageRepository = ReceivedMessageRepository.createMybatis(properties);
        System.out.println(receivedMessageRepository.save(new MessageDto(0, MessageType.SMS, new Timestamp(System.currentTimeMillis()), 2, "0104444", "04404040", "테스트")));
        System.out.println(receivedMessageRepository.deleteAll());
    }
}
