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
        $this->load->view('header', $data);
        $this->load->view('sidebar', $data);
        $this->load->view('lesson/index', $data);
        $this->load->view('footer', $data);
    }

    public function lessonsByLessonType() {
        $lessonData = $this->lesson_model->getLessonsByLessonType();
        $data['lessons'] = $lessonData->lessons;
        $this->load->view('lesson/index', $data);
    }

    public function getAjaxLessonsByLessonType($lessonId) {
        $lessonData = $this->lesson_model->getAjaxLessonsByLessonType($lessonId);
        echo(json_encode($lessonData->lessons));
    }

    public function joinLesson($lessonId) {
        $request['lessonId'] = $lessonId;
        $request['userId'] = $this->user_model->getUserByUsername($_SESSION['user']->username)->users->user->id;
        $participate = createParticipantObject($request);
        $this->lesson_model->joinLesson(json_encode($participate));
    }

    public function saveLesson() {
        $data = createLessonObject($_REQUEST);
        $data['userId'] = $this->user_model->getUserByUsername($data['createdBy'])->users->user->id;
        $this->lesson_model->saveLesson(json_encode($data));
        return $this->index();
    }

    public function create() {
        $lessonTypes = $this->lesson_model->getLessonTypes();
        $data['lessonTypes'] = $lessonTypes->lessonTypes;
        $this->load->view('header', $data);
        $this->load->view('sidebar', $data);
        $this->load->view('lesson/create', $data);
        $this->load->view('footer', $data);
    }

    public function checkIfAlreadyJoined($lessonId) {
        $check = $this->lesson_model->checkIfAlreadyJoined($lessonId,"ajax");
        $check = $check->participants;
        echo(json_encode($check));
    }

}
