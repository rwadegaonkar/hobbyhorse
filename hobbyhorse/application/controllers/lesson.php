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
    }

    public function index() {
        $lessonData = $this->lesson_model->getLessons();
        $data['lessons'] = $lessonData->lessons;
        $this->load->view('lesson/index',$data);
    } 
    public function lessonsByLessonType() {
        $lessonData = $this->lesson_model->getLessonsByLessonType();
        $data['lessons'] = $lessonData->lessons;
        $this->load->view('lesson/index',$data);
    }
}
