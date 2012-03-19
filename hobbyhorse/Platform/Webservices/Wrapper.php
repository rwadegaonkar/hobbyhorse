<?php
/**
 * Created by JetBrains PhpStorm.
 * User: radhika
 * Date: 3/18/12
 * Time: 4:58 PM
 * To change this template use File | Settings | File Templates.
 */
require_once('Zend/Http/Client.php');
require_once('Platform/Data/User.php');

class Platform_Webservices_Wrapper
{
    /**
     *
     */
    public function __construct()
    {
    }

    public function request()
    {
        $config = array(
            'adapter'       => 'Zend_Http_Client_Adapter_Socket',
            'ssltransport'  => 'tls'
        );

        $client = new Zend_Http_Client('http://192.168.1.5:8080/eggsy/app/users/0.json', $config);
        try
        {
            $response = $client->request();
            $jsonData = $this->parseZendResponse($response);
            echo $jsonData;
            $arr = json_decode($jsonData);

            echo "<pre>";
            var_dump($arr);exit;

        }
        catch(Zend_Http_Client_Adapter_Exception $e){
            throw exception('Request to webservice failed %s',$e->message());
        }
    }

    /**
     * @param Zend_Http_Response $response
     * @return json string
     * @throws
     */
    public function parseZendResponse(Zend_Http_Response $response)
    {
        if($response->getStatus() == 200) {
            return $response->getBody();
        }
        else {
            throw exception("An error occurred while fetching data: %s<br />",$response->getMessage());
        }
    }
}
