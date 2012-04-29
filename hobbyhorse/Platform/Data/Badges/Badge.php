<?php
/**
 * Created by JetBrains PhpStorm.
 * User: radhika
 * Date: 4/3/12
 * Time: 12:46 AM
 * To change this template use File | Settings | File Templates.
 */
require_once("Platform/Data/Base.php");

class Platform_Data_Badges_Badge extends Platform_Data_Base
{
    /**
     * @var
     */
    protected $_lessonCount;
    

    public function __construct()
    {

    }


    public function getLessonCount()
    {
        return $this->_lessonCount;
    }

    /**
     * @param  $lessonTypeId
     */
    public function setLessonCount($lessonCount)
    {
        $this->_lessonCount = $lessonCount;
    }
    
   
}
