<?php

/**
 * Created by JetBrains PhpStorm.
 * User: radhika
 * Date: 3/18/12
 * Time: 4:25 PM
 * To change this template use File | Settings | File Templates.
 */
class Comment extends CI_Controller {

    /**
     * constructor of User controller class
     */
    public function __construct() {
        parent::__construct();
        $this->load->model('Comment_Model', 'comment_model');
        $this->load->model('User_Model', 'user_model');
    }
    
    public function saveComment($lessonName,$description,$lessonId,$rating) {
        $request['lessonName'] = urldecode($lessonName);
        $request['description'] = urldecode($description);
        $request['lessonId'] = urldecode($lessonId);
        $request['rating'] = urldecode($rating);
        $request['userId'] = $this->user_model->getUserByUsername($_SESSION['user']->username)->users->user->id;
        $data = createCommentObject($request);
        $this->comment_model->saveComment(json_encode($data));
    }
    
    public function checkCommented($lessonId) {
        $check = $this->comment_model->checkCommented($lessonId, "ajax");
        $check = $check->comments;
        echo(json_encode($check));
    }

}
