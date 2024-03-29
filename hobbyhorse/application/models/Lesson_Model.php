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

class Lesson_Model extends CI_Model {

    public function __construct() {
        parent::__construct();
    }

    public function getLessons() {
        $myWrapper = new Platform_Webservices_Wrapper();
        $jsonObj = $myWrapper->request('lessons/');
        return Platform_Data::getDataObject($jsonObj);
    }

    public function getLessonsByLessonType($lessonId) {
        $myWrapper = new Platform_Webservices_Wrapper();
        $jsonObj = $myWrapper->request('lessons/lessontype/' . $lessonId);
        return Platform_Data::getDataObject($jsonObj);
    }

    public function getLessonsByLessonId($lessonId) {
        $myWrapper = new Platform_Webservices_Wrapper();
        $jsonObj = $myWrapper->request('lessons/id/' . $lessonId);
        return Platform_Data::getDataObject($jsonObj);
    }

    public function getLessonTypes() {
        $myWrapper = new Platform_Webservices_Wrapper();
        $jsonObj = $myWrapper->request('lessontypes/');
        return Platform_Data::getDataObject($jsonObj);
    }

    public function getAjaxLessonsByLessonType($lessonId) {
        $myWrapper = new Platform_Webservices_Wrapper();
        $jsonObj = $myWrapper->request('lessons/lessontype/' . $lessonId);
        return $jsonObj;
    }

    public function getLessonsByUsername() {
        $myWrapper = new Platform_Webservices_Wrapper();
        $jsonObj = $myWrapper->request('lessons/user/' . $_SESSION['user']->username);
        return Platform_Data::getDataObject($jsonObj);
    }

    public function getLatestLesson($userId) {
        $myWrapper = new Platform_Webservices_Wrapper();
        $jsonObj = $myWrapper->request('lessons/lastlesson/' . $userId);
        return Platform_Data::getDataObject($jsonObj);
    }

    public function joinLesson($participate) {
        $myWrapper = new Platform_Webservices_Wrapper();
        $jsonObj = $myWrapper->request('participants/join', "POST", $participate);
        header('location: ' . base_url() . 'index.php/lesson/');
    }

    public function saveLesson($lesson) {
        $myWrapper = new Platform_Webservices_Wrapper();
        $jsonObj = $myWrapper->request('lessons/add', "POST", $lesson);
        header('location: ' . base_url() . 'index.php/lesson/');
    }

    public function checkIfAlreadyJoined($lessonId, $method=null) {
        $myWrapper = new Platform_Webservices_Wrapper();
        $jsonObj = $myWrapper->request('participants/checkjoined/' . $lessonId . '/' . $_SESSION['user']->username);
        if (!$method) {
            return Platform_Data::getDataObject($jsonObj);
        } else {
            return $jsonObj;
        }
    }
    
    public function getLessonsByExpert($username, $method=null) {
        $myWrapper = new Platform_Webservices_Wrapper();
        $jsonObj = $myWrapper->request('lessons/expert/' . $username);
        if (!$method) {
            return Platform_Data::getDataObject($jsonObj);
        } else {
            return $jsonObj;
        }
    }

    public function lessonsAttended($method=null) {
        $myWrapper = new Platform_Webservices_Wrapper();
        $jsonObj = $myWrapper->request('lessons/attended/' . $_SESSION['user']->username);
        if (!$method) {
            return Platform_Data::getDataObject($jsonObj);
        } else {
            return $jsonObj;
        }
    }

    public function getSuggestedLessons($name, $category, $method=null) {
        $myWrapper = new Platform_Webservices_Wrapper();
        $clean_name = preg_replace('/[^a-zA-Z0-9.]/', '', $name);
        $clean_category = preg_replace('/[^a-zA-Z0-9.]/', '', $category);
        $jsonObj = $myWrapper->request('lessons/suggest/' . strtolower($clean_name) . "/" . strtolower($clean_category) . "/" . $_SESSION['user']->username);
        if (!$method) {
            return Platform_Data::getDataObject($jsonObj);
        } else {
            return $jsonObj;
        }
    }
    
    public function getSuggestedLessonsApriori($lessonid, $method=null) {
        $myWrapper = new Platform_Webservices_Wrapper();
        $jsonObj = $myWrapper->request('lessons/suggestapriori/' . $lessonid);
        if (!$method) {
            return Platform_Data::getDataObject($jsonObj);
        } else {
            return $jsonObj;
        }
    }

    public function updateLessonIsLive($lesson) {
        $myWrapper = new Platform_Webservices_Wrapper();
        $myWrapper->request('lessons/updateislive', "POST", $lesson);
    }

    public function updateWasAttended($participant) {
        $myWrapper = new Platform_Webservices_Wrapper();
        $myWrapper->request('participants/updatewasattended', "POST", $participant);
    }

    public function getAllBadges() {
        $myWrapper = new Platform_Webservices_Wrapper();
        $jsonObj = $myWrapper->request('badges/');
        return Platform_Data::getDataObject($jsonObj);
    }

}
