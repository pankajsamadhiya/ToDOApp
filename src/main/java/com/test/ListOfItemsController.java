package com.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;



@RestController
public class ListOfItemsController {

    @Autowired
    ItemRepository itemRepository;

    @RequestMapping(value="/resource/list/", method= RequestMethod.GET)
    List listOfItems() {
        Iterable<Item> iterable = itemRepository.findAll();
        List<Item> result = new ArrayList<>();
        iterable.iterator().forEachRemaining(result::add);
        return result;
    }

    @RequestMapping(value="/resource/dummylist/", method= RequestMethod.GET)
    List listOfDummyItems() {
        List<Map<String, String>> result = new ArrayList<Map<String, String>>();
        Map<String, String> map1 = new HashMap<String, String>();
        map1.put("id", "1");
        map1.put("title", "Go for a swim");
        map1.put("content", "Go swimming on Monday night");
        map1.put("done","yes");
        result.add(0, map1);

        Map<String, String> map2 = new HashMap<String, String>();
        map2.put("id", "2");
        map2.put("title", "Visit farmer's market");
        map2.put("content", "Buy dairy and eggs at farmers market on Wednesday");
        map2.put("done","no");
        result.add(1, map2);

        return result;
    }

    /**
     * Change the 'done' flag on a todo item
     * @param id: of todo item
     * @param done:  yes or no
     * @return ok message
     */
    @RequestMapping(value="/resource/done/{id}/{done}/", method=RequestMethod.POST)
    String postDoneUpdate(@PathVariable long id, @PathVariable String done) {
        Item item = itemRepository.findOne(id);
        if (done.equals("yes") || done.equals("no")) {
            item.done = done;
        } else {
          System.out.println("Invalid argument to postDoneUpdate:  " + done);
        }
        itemRepository.save(item);
        return "[\"ok\"]";
    }

    // post('/resource/save/<id>/title/content/done/')

    /**
     * Save a todo item
     * @param id
     * @param title
     * @param content
     * @param done
     * @return
     */
    @RequestMapping(value="/resource/save/{id}/{title}/{content}/{done}/", method=RequestMethod.POST)
    String postSaveUpdate(@PathVariable long id, @PathVariable String title, @PathVariable String content, @PathVariable String done) {
        Item item = itemRepository.findOne(id);
        if (done.equals("yes") || done.equals("no")) {
            item.done = done;
        } else {
            System.out.println("Invalid argument to postSaveUpdate:  " + done);
        }
        item.title = title;
        item.content = content;
        itemRepository.save(item);
        return "[\"ok\"]";
    }


    //  post('/resource/create/') -> returns id.


    /**
     * Create a new todo item
     * @return full item representation, with empty fields
     */
    @RequestMapping(value="/resource/create/", method=RequestMethod.GET)
    Item postSaveUpdate() {
        Item item = itemRepository.save(new Item());
        return item;
    }

}
