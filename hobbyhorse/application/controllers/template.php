<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class Template extends CI_Controller {

    /**
     * Index Page for this controller.
     *
     * Maps to the following URL
     * 		http://example.com/index.php/welcome
     *	- or -
     * 		http://example.com/index.php/welcome/index
     *	- or -
     * Since this controller is set as the default controller in
     * config/routes.php, it's displayed at http://example.com/
     *
     * So any other public methods not pre  fixed with an underscore will
     * map to /index.php/welcome/<method_name>
     * @see http://codeigniter.com/user_guide/general/urls.html
     */
    public function index()
    {

        $data['title'] = 'Hobbyhorse - Home';           /* REQUIRED FIELD - title */
        //$data['css'] = array('my.css','abc.css');     /* OPTIONAL: only if u want to add page specific css */
        //$data['js'] = array('my.js','abc.js');        /* OPTIONAL: only if u want to add page specific css */
        $data['header_text'] = 'Login';                 /* REQUIRED FIELD - header title */
        $data['header_content'] = $this->load->view('common/login_box','',true);
        $data_content['abc'] = 'blah blah';
        $data['content'] = $this->load->view('content/home_content',$data_content,true);
        $data['sidebar'] = $this->load->view('common/right-sidebar','',true);
        $this->load->view('templates/home',$data);
    }
}

/* End of file welcome.php */
/* Location: ./application/controllers/welcome.php */