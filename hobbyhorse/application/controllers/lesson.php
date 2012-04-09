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
        $lessonData = $this->lesson_model->getLessonsByLessonType("1");
        $lessonTypes = $this->lesson_model->getLessonTypes();
        $data['lessons'] = $lessonData->lessons;
        $data['lessonTypes'] = $lessonTypes->lessonTypes;
        $this->load->view('header',$data);
        $this->load->view('sidebar',$data);
        $this->load->view('lesson/index',$data);
        $this->load->view('footer',$data);
    } 
    
    public function lessonsByLessonType() {
        $lessonData = $this->lesson_model->getLessonsByLessonType();
        $data['lessons'] = $lessonData->lessons;
        $this->load->view('lesson/index',$data);
    }
    
    public function getAjaxLessonsByLessonType($lessonId) {
        $lessonData = $this->lesson_model->getAjaxLessonsByLessonType("1");
        echo(json_encode($lessonData->lessons));
        }
}
