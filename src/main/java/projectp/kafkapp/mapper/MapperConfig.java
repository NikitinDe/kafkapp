package projectp.kafkapp.mapper;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import projectp.kafkapp.model.ClientsInfo;
import projectp.kafkapp.model.ClientsModel;
import projectp.kafkapp.smsFormat.SmsMessage;

@Mapper(componentModel = "spring")
public interface MapperConfig {

    @Mapping(target = "fullName", expression = "java(clientsInfo.getName() + ' '" +
            " + clientsInfo.getMiddleName() + ' ' + clientsInfo.getSurname())")
    @Mapping(source = "phone", target = "phone")
    @Mapping(source = "birthday", target = "birthday")
    @Mapping(target = "messageSend", ignore = true)
    ClientsModel toModel(ClientsInfo clientsInfo);

    @Mapping(source = "client.phone", target = "phone")
    @Mapping(target = "message", expression = "java(String.format(\"%s, в этом месяце для вас действует скидка %d\", client.getFullName(), discount))")
    SmsMessage toSmsMessage(ClientsModel client, @Context Integer discount);

}





