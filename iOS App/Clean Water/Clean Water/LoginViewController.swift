//
//  LoginViewController.swift
//  Clean Water
//
//  Created by Varun Ballari on 2/22/17.
//  Copyright © 2017 Varun Ballari. All rights reserved.
//

import UIKit

class LoginViewController: UIViewController, UITextFieldDelegate {

    @IBOutlet var usernameTextField: UITextField!
    @IBOutlet var passwordTextField: UITextField!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        usernameTextField.delegate = self
        passwordTextField.delegate = self
        usernameTextField.becomeFirstResponder()


        // Do any additional setup after loading the view.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    @IBAction func cancel(_ sender: Any) {
        usernameTextField.resignFirstResponder()
        self.dismiss(animated: true, completion: nil)
    }

    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        // Try to find next responder
        if textField == usernameTextField {
            passwordTextField.becomeFirstResponder()
            usernameTextField.resignFirstResponder()
        } else {
            passwordTextField.resignFirstResponder()
            
            if (usernameTextField.text == "admin" && passwordTextField.text == "password") {
                self.performSegue(withIdentifier: "goToHome", sender: self)
            }
        }
        // Do not add a line break
        return false
    }
    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
    */

}
