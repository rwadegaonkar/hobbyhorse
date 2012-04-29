<?php

/**
 * Created by JetBrains PhpStorm.
 * User: radhika
 * Date: 3/18/12
 * Time: 4:25 PM
 * To change this template use File | Settings | File Templates.
 */
class Lesson extends CI_Controller {

    /**
     * constructor of User controller class
     */
    public function __construct() {
        parent::__construct();
        $this->load->model('Lesson_Model', 'lesson_model');
        $this->load->model('User_Model', 'user_model');
        $this->load->model('Comment_Model', 'comment_model');
    }

    public function index() {
        $lessonData = $this->lesson_model->getLessonsByLessonType("1");
        $lessonTypes = $this->lesson_model->getLessonTypes();
        $data['lessons'] = $lessonData->lessons;
        foreach ($data['lessons']->data as $l) {
            $check = $this->lesson_model->checkIfAlreadyJoined($l->id);
            $check = $check->participants->data;
            if (!$check) {
                $data['joined'][$l->id] = 0;
            } else {
                $data['joined'][$l->id] = 1;
            }
        }
        $data['lessonTypes'] = $lessonTypes->lessonTypes;
        $data['title'] = 'Hobbyhorse - Browse Lessons';
        $data['content'] = $this->load->view('lesson/index', $data, true);
        $data['sidebar'] = $this->load->view('common/right-sidebar', '', true);
        $this->load->view('templates/main', $data);
    }

    public function lessonsByLessonType() {
        $lessonData = $this->lesson_model->getLessonsByLessonType();
        $data['lessons'] = $lessonData->lessons;
        $data['title'] = 'Hobbyhorse - Lessons By Category';
        $data['content'] = $this->load->view('lesson/index', $data, true);
        $data['sidebar'] = $this->load->view('common/right-sidebar', '', true);
        $this->load->view('templates/main', $data);
    }

    public function getAjaxLessonsByLessonType($lessonId) {
        $lessonData = $this->lesson_model->getAjaxLessonsByLessonType($lessonId);
        echo(json_encode($lessonData->lessons));
    }
    
    public function updateLessonIsLive($lessonId, $isLiveVal) {
        $data['name'] = 'a';
        $data['description'] = "a";
        $data['eventDate'] = "a";
        $data['eventTime'] = "a";
        $data['createdBy'] = $_SESSION['user']->username;
        $data['lastUpdatedBy'] = $_SESSION['user']->username;
        $data['lessonTypeId'] = 1;
        $data['sessionId'] = "";
        $data['isLive'] = $isLiveVal;
        $data['id'] = $lessonId;
        $this->lesson_model->updateLessonIsLive(json_encode($data));
    }
    
    public function updateWasAttended($lessonId) {
        $data['name'] = 'a';
        $data['description'] = "a";
        $data['createdBy'] = $_SESSION['user']->username;
        $data['lastUpdatedBy'] = $_SESSION['user']->username;
        $data['lessonId'] = (int)$lessonId;
        $data['id'] = (int)$lessonId;
        $data['userId'] = 0;
        $data['wasAttended'] = 1;
        $this->lesson_model->updateWasAttended(json_encode($data));
    }

    public function joinLesson($lessonId, $lessonName) {
        $request['lessonId'] = $lessonId;
        $request['lessonName'] = $lessonName;
        $request['userId'] = $this->user_model->getUserByUsername($_SESSION['user']->username)->users->user->id;
        $participate = createParticipantObject($request);
        $this->lesson_model->joinLesson(json_encode($participate));
    }

    public function saveLesson() {
        $data = createLessonObject($_REQUEST);
        $data['userId'] = $this->user_model->getUserByUsername($data['createdBy'])->users->user->id;
        $this->lesson_model->saveLesson(json_encode($data));
        $lessonObj = $this->lesson_model->getLatestLesson($data['userId'])->lessons->lesson;
        $data['lessonId'] = $lessonObj->id;
        $data['lessonName'] = $lessonObj->name;
        $participate = createParticipantObject($data);
        $this->lesson_model->joinLesson(json_encode($participate));
        return $this->index();
    }

    public function create() {
        $lessonTypes = $this->lesson_model->getLessonTypes();
        $data['lessonTypes'] = $lessonTypes->lessonTypes;
        $data['title'] = 'Hobbyhorse - Create Lesson';
        $data['content'] = $this->load->view('lesson/create', $data, true);
        $data['sidebar'] = $this->load->view('common/right-sidebar', '', true);
        $this->load->view('templates/main', $data);
    }

    public function checkIfAlreadyJoined($lessonId) {
        $check = $this->lesson_model->checkIfAlreadyJoined($lessonId, "ajax");
        $check = $check->participants;
        echo(json_encode($check));
    }

    public function joinedLesson() {
        $lessonData = $this->lesson_model->getLessonsByUsername();
        $data['lessons'] = $lessonData->lessons;
        $data['title'] = 'Hobbyhorse - My Forthcoming Lessons';
        $data['content'] = $this->load->view('lesson/joinedLesson', $data, true);
        $data['sidebar'] = $this->load->view('common/right-sidebar', '', true);
        $this->load->view('templates/main', $data);
    }

    public function main($lessonId) {
        $lessonData = $this->lesson_model->getLessonsByLessonId($lessonId);
        $comments = $this->comment_model->getCommentsForLesson($lessonId);
        $data['comments'] = $comments->comments;
        $data['lesson'] = $lessonData->lessons->lesson;
        $data['title'] = 'Hobbyhorse - My Forthcoming Lessons';
        $data['content'] = $this->load->view('lesson/main', $data, true);
        $data['sidebar'] = $this->load->view('common/right-sidebar', '', true);
        $this->load->view('templates/main', $data);
    }

}
