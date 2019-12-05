package com.crud.tasks.trello.mapper;
import com.crud.tasks.domain.*;
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
public class TrelloMapperTestSuite {
    @Autowired
    private TrelloMapper trelloMapper;

    @Test
    public void testBoards() {
        //Given
        List<TrelloBoard> trelloBoards = new ArrayList<>();
        trelloBoards.add(new TrelloBoard("1", "first", new ArrayList<>()));
        trelloBoards.add(new TrelloBoard("2", "second", new ArrayList<>()));
        trelloBoards.add(new TrelloBoard("3", "third", new ArrayList<>()));
        List<TrelloBoardDto> trelloBoardsDto = new ArrayList<>();
        trelloBoardsDto.add(new TrelloBoardDto("4", "fourth", new ArrayList<>()));
        trelloBoardsDto.add(new TrelloBoardDto("5", "fifth", new ArrayList<>()));
        trelloBoardsDto.add(new TrelloBoardDto("6", "sixth", new ArrayList<>()));
        trelloBoardsDto.add(new TrelloBoardDto("7", "seventh", new ArrayList<>()));
        //When
        List<TrelloBoardDto> mappingToDto = trelloMapper.mapToBoardsDto(trelloBoards);
        List<TrelloBoard> mappingFromDto = trelloMapper.mapToBoards(trelloBoardsDto);
        //Then
        Assert.assertEquals(3, mappingToDto.size());
        Assert.assertEquals(4, mappingFromDto.size());
        Assert.assertEquals(trelloBoards.get(1).getName(), mappingToDto.get(1).getName());
        Assert.assertEquals(trelloBoardsDto.get(3).getName(), mappingFromDto.get(3).getName());
    }
    @Test
    public void testLists() {
        //Given
        List<TrelloList> trelloList = new ArrayList<>();
        trelloList.add(new TrelloList("1", "first", false));
        trelloList.add(new TrelloList("2", "second", true));
        trelloList.add(new TrelloList("3", "third", true));
        List<TrelloListDto> trelloListDto = new ArrayList<>();
        trelloListDto.add(new TrelloListDto("4", "fourth", true));
        trelloListDto.add(new TrelloListDto("5", "fifth", true));
        trelloListDto.add(new TrelloListDto("6", "sixth", false));
        trelloListDto.add(new TrelloListDto("7", "seventh", false));
        //When
        List<TrelloListDto> mappingToDto = trelloMapper.mapToListDto(trelloList);
        List<TrelloList> mappingFromDto = trelloMapper.mapToList(trelloListDto);
        //Then
        Assert.assertEquals(3, mappingToDto.size());
        Assert.assertEquals(4, mappingFromDto.size());
        Assert.assertEquals(trelloList.get(2).getId(), mappingToDto.get(2).getId());
        Assert.assertEquals(trelloListDto.get(1).isClosed(), mappingFromDto.get(1).isClosed());
    }
    @Test
    public void testCards() {
        //Given
        TrelloCard trelloCard = new TrelloCard("first", "test card", "x", "1");
        TrelloCardDto trelloCardDto = new TrelloCardDto("second", "test 2 card", "y", "2");
        //When
        TrelloCardDto mappedToDto = trelloMapper.mapToCardDto(trelloCard);
        TrelloCard mappedFromDto = trelloMapper.mapToCard(trelloCardDto);
        //Then
        Assert.assertEquals(trelloCard.getPos(), mappedToDto.getPos());
        Assert.assertEquals(trelloCardDto.getDescription(), mappedFromDto.getDescription());
    }
}
