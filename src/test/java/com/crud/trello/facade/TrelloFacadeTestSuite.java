package com.crud.trello.facade;
import com.crud.tasks.domain.TrelloBoard;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.mapper.TrelloMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TrelloFacadeTestSuite {

    @Autowired
    private TrelloMapper trelloMapper;

    @Test
    public void testBoards() {
        List<TrelloBoard> trelloBoards = new ArrayList<>();
        trelloBoards.add(new TrelloBoard("1", "first", new ArrayList<>()));
        trelloBoards.add(new TrelloBoard("2", "second", new ArrayList<>()));
        trelloBoards.add(new TrelloBoard("3", "third", new ArrayList<>()));
        trelloBoards.add(new TrelloBoard("4", "fourth", new ArrayList<>()));

        List<TrelloBoardDto> trelloBoardsDto = new ArrayList<>();
        trelloBoardsDto.add(new TrelloBoardDto("5", "fifth", new ArrayList<>()));
        trelloBoardsDto.add(new TrelloBoardDto("6", "sixth", new ArrayList<>()));
        trelloBoardsDto.add(new TrelloBoardDto("7", "seventh", new ArrayList<>()));
        trelloBoardsDto.add(new TrelloBoardDto("8", "eighth", new ArrayList<>()));
        trelloBoardsDto.add(new TrelloBoardDto("9", "ninth", new ArrayList<>()));

        List<TrelloBoardDto> mappingToDto = trelloMapper.mapToBoardsDto(trelloBoards);
        List<TrelloBoard> mappingFromDto = trelloMapper.mapToBoards(trelloBoardsDto);

        Assert.assertEquals(4, mappingToDto.size());
        Assert.assertEquals(5, mappingFromDto.size());
    }
}
