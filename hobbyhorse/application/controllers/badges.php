<?php


/**
 * Created by JetBrains PhpStorm.
 * User: radhika
 * Date: 3/18/12
 * Time: 4:25 PM
 * To change this template use File | Settings | File Templates.
 */
class Badges extends CI_Controller {

    /**
     * constructor of User controller class
     */
    public function __construct() {
        parent::__construct();
        $this->load->model('Lesson_Model', 'lesson_model');
        $this->load->model('User_Model', 'user_model');
    }
    
    public function index() {
        $lessonData = $this->lesson_model->lessonsAttended();
        $badgeData = $this->lesson_model->getAllBadges();
        $data['lessons'] = $lessonData->lessons;
        $data['badges'] = $badgeData->badges;
        $data['title'] = 'Hobbyhorse - Lessons Attended By Me';
        $data['content'] = $this->load->view('badges/index', $data, true);
        $data['sidebar'] = $this->load->view('common/right-sidebar', '', true);
        $this->load->view('templates/main', $data);
    }

}
?>
