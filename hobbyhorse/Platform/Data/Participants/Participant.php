<?php

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
require_once("Platform/Data/Base.php");

class Platform_Data_Participants_Participant extends Platform_Data_Base {

    /**
     * @var
     */
    protected $_lessonId;

    /**
     * @var
     */
    protected $_userId;

    public function getLessonId() {
        return $this->_lessonId;
    }

    /**
     * @param  $lessonTypeId
     */
    public function setLessonId($lessonId) {
        $this->_lessonId = $lessonId;
    }

    public function getUserId() {
        return $this->_userId;
    }

    /**
     * @param  $lessonTypeId
     */
    public function setUserId($userId) {
        $this->_userId = $userId;
    }

}

?>