<?php
/**
 * Created by JetBrains PhpStorm.
 * User: radhika
 * Date: 4/3/12
 * Time: 12:46 AM
 * To change this template use File | Settings | File Templates.
 */
require_once("Platform/Data/Base.php");

class Platform_Data_Lessons_Lesson extends Platform_Data_Base
{
    /**
     * @var
     */
    protected $_lessonTypeId;
    /**
     * @var
     */
    protected $_userId;
/**
     * @var
     */
    protected $_username;/**
     * @var
     */
    protected $_eventDate;/**
     * @var
     */
    protected $_eventTime;
    
    /* @var
     */
    protected $_sessionId;

    /**
     * @return
     */

    public function __construct()
    {

    }


    public function getLessonTypeId()
    {
        return $this->_lessonTypeId;
    }

    /**
     * @param  $lessonTypeId
     */
    public function setLessonTypeId($lessonTypeId)
    {
        $this->_lessonTypeId = $lessonTypeId;
    }
    
    public function getUserId()
    {
        return $this->_userId;
    }

    /**
     * @param  $lessonTypeId
     */
    public function setUserId($userId)
    {
        $this->_userId = $userId;
    }
    
    public function getUsername()
    {
        return $this->_username;
    }

    /**
     * @param  $lessonTypeId
     */
    public function setUsername($username)
    {
        $this->_username = $username;
    }
    
    public function getEventDate()
    {
        return $this->_eventDate;
    }

    /**
     * @param  $eventDate
     */
    public function setEventDate($eventDate)
    {
        $this->_eventDate = $eventDate;
    }
    public function getEventTime()
    {
        return $this->_eventTime;
    }

    /**
     * @param  $lessonTypeId
     */
    public function setEventTime($eventTime)
    {
        $this->_eventTime = $eventTime;
    }
    
     public function getSessionId()
    {
        return $this->_sessionId;
    }

    /**
     * @param  $lessonTypeId
     */
    public function setSessionId($sessionId)
    {
        $this->_sessionId = $sessionId;
    }
    
}
