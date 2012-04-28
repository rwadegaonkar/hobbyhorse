<?php
/**
 * Created by JetBrains PhpStorm.
 * User: radhika
 * Date: 4/3/12
 * Time: 12:46 AM
 * To change this template use File | Settings | File Templates.
 */
require_once("Platform/Data/Base.php");

class Platform_Data_Comments_Comment extends Platform_Data_Base
{
    /**
     * @var
     */
    protected $_lessonId;
    /**
     * @var
     */
    protected $_userId;
/**
     * @var
     */
    protected $_rating;/**

    /**
     * @return
     */

    public function __construct()
    {

    }


    public function getLessonId()
    {
        return $this->_lessonId;
    }

    /**
     * @param  $lessonTypeId
     */
    public function setLessonId($lessonId)
    {
        $this->_lessonId = $lessonId;
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
    
    public function getRating()
    {
        return $this->_rating;
    }

    /**
     * @param  $lessonTypeId
     */
    public function setRating($rating)
    {
        $this->_rating = $rating;
    }
   
}
