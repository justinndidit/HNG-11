import {Router} from 'express';
import { getUserDetailsAndGreet } from '../handlers/appHandler';

const router = Router();


router.get("/", getUserDetailsAndGreet)



export default router;