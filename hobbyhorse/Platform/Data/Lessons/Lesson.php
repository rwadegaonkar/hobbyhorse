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

    
}
