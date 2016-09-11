package com.moge.gege.model;

import java.util.List;

public class TopicPostListModel
{
    private String previous_cursor;
    private String next_cursor;
    private List<TopicPostModel> posts;

    public String getPrevious_cursor()
    {
        return previous_cursor;
    }

    public void setPrevious_cursor(String previous_cursor)
    {
        this.previous_cursor = previous_cursor;
    }

    public String getNext_cursor()
    {
        return next_cursor;
    }

    public void setNext_cursor(String next_cursor)
    {
        this.next_cursor = next_cursor;
    }

    public List<TopicPostModel> getPosts()
    {
        return posts;
    }

    public void setPosts(List<TopicPostModel> posts)
    {
        this.posts = posts;
    }
}
