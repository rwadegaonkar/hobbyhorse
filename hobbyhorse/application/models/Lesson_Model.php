<?php
/**
 * Created by JetBrains PhpStorm.
 * User: radhika
 * Date: 3/18/12
 * Time: 4:27 PM
 * To change this template use File | Settings | File Templates.
 */

require_once('Platform/Webservices/Wrapper.php');
require_once('Platform/Data.php');

class Lesson_Model extends CI_Model
{
    public function __construct()
    {
        parent::__construct();
    }

    public function getLessons()
    {
        $myWrapper = new Platform_Webservices_Wrapper();
        $jsonObj =  $myWrapper->request('lessons/');
        return  Platform_Data::getDataObject($jsonObj);
    }
    public function getLessonsByLessonType()
    {
        $myWrapper = new Platform_Webservices_Wrapper();
        $jsonObj =  $myWrapper->request('lessons/lessontype/1');
        return  Platform_Data::getDataObject($jsonObj);
    }
}
